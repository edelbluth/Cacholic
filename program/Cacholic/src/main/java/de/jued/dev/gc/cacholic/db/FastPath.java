package de.jued.dev.gc.cacholic.db;

import de.jued.dev.gc.cacholic.Cacholic;
import de.jued.dev.gc.cacholic.CacholicException;
import de.jued.dev.gc.cacholic.db.entities.Config;
import de.jued.dev.gc.cacholic.db.entities.Version;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

/**
 * Get some database data directly. This is a fast path to some important
 * information.
 * 
 * @author Juergen Edelbluth <dev@jued.de>
 * @since 0.1
 * @version 0.1
 */
public class FastPath
{
    
    /**
     * Get the database Version
     * 
     * @return the database Version
     */
    public static long getDatabaseVersion() throws CacholicException
    {
        try
        {
            final EntityManager em = Cacholic.getInstance().getEntityManagerFactory().createEntityManager();
            final TypedQuery<Version> tqv = em.createNamedQuery("Version.highToLow", Version.class);
            tqv.setMaxResults(1);
            final Version result = tqv.getSingleResult();
            em.close();
            return result.getVersion();
        }
        catch (Exception ex)
        {
            throw new CacholicException("Unable to get Version Information.", ex);
        }
    }
    
    /**
     * Get an configuration object from the database
     * 
     * @param key Key of the object
     * @return The object or <code>null</code> if not found.
     */
    public static Config getConfig(final String key)
    {
        try
        {
            final EntityManager em = Cacholic.getInstance().getEntityManagerFactory().createEntityManager();
            final TypedQuery<Config> tqc = em.createNamedQuery("Config.getByKey", Config.class);
            final Config result = tqc.getSingleResult();
            em.close();
            return (null == result ? null : result);
        }
        catch (Exception ex)
        {
            Logger.getLogger(FastPath.class.getCanonicalName()).log(Level.WARNING, String.format("Unable to get configuration object for '%s'.", key), ex);
            return null;
        }
        
    }
    
    /**
     * Get a value from the configuration
     * 
     * @param key The key
     * @param defValue The value if key not found or on error
     * @return The value 
     */
    public static Serializable getConfigValue(final String key, final Serializable defValue)
    {
        final Config c = FastPath.getConfig(key);
        return (null == c ? defValue : c.getValue());
    }
    
    /**
     * Get a value from the configuration.
     * 
     * @param key The key
     * @return The value; or <code>null</code> if not found or on error
     */
    public static Serializable getConfigValue(final String key)
    {
        return FastPath.getConfigValue(key, null);
    }
    
    /**
     * Write a value to the database
     * 
     * @param key The key
     * @param value The value
     * @return <code>true</code> if everything worked
     */
    public boolean setConfigValue(final String key, final Serializable value)
    {
        try
        {
            final EntityManager em = Cacholic.getInstance().getEntityManagerFactory().createEntityManager();
            final EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            Config c = FastPath.getConfig(key);
            if (null == c)
            {
                c = new Config();
                c.setKey(key);
                c.setValue(value);
                em.persist(c);
            }
            else
            {
                c.setValue(value);
                em.merge(c);
            }
            transaction.commit();
            em.close();
            return true;
        }
        catch (Exception e)
        {
            Logger.getLogger(FastPath.class.getCanonicalName()).log(Level.SEVERE, String.format("Unable to save config data for '%s'", key), e);
            return false;
        }
    }
    
}
