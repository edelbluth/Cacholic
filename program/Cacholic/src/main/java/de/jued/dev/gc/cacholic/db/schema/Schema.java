package de.jued.dev.gc.cacholic.db.schema;

import de.jued.dev.gc.cacholic.CacholicException;
import de.jued.dev.lib.libjued.Stream;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handle the database Schema
 * 
 * @author Juergen Edelbluth <dev@jued.de>
 * @version 0.1
 * @since 0.1
 */
public class Schema
{
    
    public static final String PROTOCOL = "jdbc:derby:";
    public static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    
    private static String SHUTDOWN_URL = String.format("%s;shutdown=true", PROTOCOL);
    
    /**
     * Upgrade to the newest version available
     * 
     * @param connection The database connection
     * @param fromVersion The last known version
     */
    private static void upgrade(final Connection connection, final long fromVersion) throws CacholicException
    {
        final long nextVersion = fromVersion + 1;
        final String nextSchemaFile = String.format("schema_%010d.sql", nextVersion);
        //Logger.getLogger(Schema.class.getCanonicalName()).log(Level.INFO, String.format("Trying schema file '%s'", nextSchemaFile));
        InputStream schemaInputStream;
        try
        {
            schemaInputStream = Schema.class.getResourceAsStream(nextSchemaFile);
        }
        catch (NullPointerException ex)
        {
            // Nothing to do, we're done!
            return;
        }
        final BufferedInputStream bis = new BufferedInputStream(schemaInputStream);
        InputStreamReader isr;
        try
        {
            isr = new InputStreamReader(schemaInputStream);
        }
        catch (NullPointerException ex)
        {
            // Nothing to do, we're done!
            return;
        }
        Logger.getLogger(Schema.class.getCanonicalName()).log(Level.INFO, String.format("Updating database to schema '%d'", nextVersion));
        final BufferedReader reader = new BufferedReader(isr);
        final StringBuilder b = new StringBuilder();
        String line = null;
        try
        {
            while (null != (line = reader.readLine()))
            {
                b.append(line);
                b.append("\n");
            }
        }
        catch (IOException ex)
        {
            Stream.close(reader);
            Stream.close(isr);
            Stream.close(bis);
            Stream.close(schemaInputStream);
            throw new CacholicException(String.format("Unable to read schema file '%s'.", nextSchemaFile), ex);
        }
        Stream.close(reader);
        Stream.close(isr);
        Stream.close(bis);
        Stream.close(schemaInputStream);
        final String s = b.toString();
        final String scripts[] = s.split(";");
        String scr = null;
        try
        {
            for (final String script : scripts)
            {
                if (null == script)
                {
                    continue;
                }
                scr = script.trim();
                if (scr.length() <= 0)
                {
                    continue;
                }
                final PreparedStatement p = connection.prepareStatement(scr);
                p.execute();
            }
            connection.commit();
        }
        catch (SQLException ex)
        {
            throw new CacholicException(String.format("Error upgrading database to version '%d'. This snippet brought the error:\n%s", nextVersion, (null != scr ? scr : "unknown")), ex);
        }
        Schema.upgrade(connection, nextVersion);
    }
    
    /**
     * Initialize the upgrade procedure
     * 
     * @param connection The database connection
     * @param create Shall all tables been created?
     * @throws CacholicException on any error
     */
    public static void upgrade(final Connection connection, final boolean create) throws CacholicException
    {
        if (create)
        {
            Logger.getLogger(Schema.class.getCanonicalName()).log(Level.INFO, "Starting new Schema by Zero");
            Schema.upgrade(connection, 0L);
            return;
        }
        PreparedStatement pstmt;
        try
        {
            pstmt = connection.prepareStatement("SELECT VERSION_NO FROM DB_VERSION ORDER BY VERSION_NO ASC");
        }
        catch (SQLException ex)
        {
            throw new CacholicException("Unable to create version check statement", ex);
        }
        ResultSet res;
        try
        {
            res = pstmt.executeQuery();
        }
        catch (SQLException ex)
        {
            throw new CacholicException("Unable to execute version check statement", ex);
        }
        if (null == res)
        {
            throw new CacholicException("Unable to read version check results (gone null)");
        }
        long version = -1;
        try
        {
            while (res.next())
            {
                version = res.getLong("VERSION_NO");
                Logger.getLogger(Schema.class.getCanonicalName()).log(Level.INFO, String.format("Found schema version '%d'", version));
            }
        }
        catch (SQLException ex)
        {
            throw new CacholicException("Unable to read version check results", ex);
        }
        try
        {
            res.close();
        }
        catch (SQLException ex)
        {
            throw new CacholicException("Unable to close version result. At this moment we should consider this as an error.", ex);
        }
        if (version <= 0)
        {
            throw new CacholicException("Unable to get current version from DB");
        }
        Schema.upgrade(connection, version);
    }

    /**
     * Check and upgrade the database schema
     * 
     * @param url JDBC URL
     * @param create Create new database first
     * @throws CacholicException on any error
     */
    public static void check(final String url, final boolean create) throws CacholicException
    {
        try
        {
            Class.forName(Schema.DRIVER).newInstance();
        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex)
        {
            throw new CacholicException("Unable to load database driver.", ex);
        }
        Connection c;
        try
        {
            c = DriverManager.getConnection(create ? String.format("%s;create=true", url) : url);
            c.setAutoCommit(false);
        }
        catch (SQLException ex)
        {
            throw new CacholicException("Unable to connect to database.", ex);
        }
        Schema.upgrade(c, create);
        Schema.SHUTDOWN_URL = String.format("%s;shutdown=true", url);
        Schema.shutdown();
    }
    
    /**
     * Shutdown the derby database
     * 
     * @throws CacholicException on error
     */
    public static void shutdown() throws CacholicException
    {
        try
        {
            DriverManager.getConnection(Schema.SHUTDOWN_URL);
        }
        catch (SQLException ex)
        {
            // An Derby shutdown always results in an SQL exception.
            // Everything's OK.
        }
        catch (Exception ex)
        {
            throw new CacholicException("Unable to close database connection.", ex);            
        }        
    }
    
}
