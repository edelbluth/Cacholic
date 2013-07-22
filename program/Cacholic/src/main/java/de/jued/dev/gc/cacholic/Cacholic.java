package de.jued.dev.gc.cacholic;

import de.jued.dev.gc.cacholic.db.schema.Schema;
import de.jued.dev.gc.cacholic.app.Gui;
import de.jued.dev.gc.cacholic.db.Closer;
import de.jued.dev.gc.cacholic.lang.Language;
import de.jued.dev.lib.libjued.CommandLine;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * This is the main class for the application, instrumentalized by the bootstrap.
 * 
 * @author Juergen Edelbluth <dev@jued.de>
 * @version 0.1
 * @since 0.1
 */
public class Cacholic implements EventListener
{
    
    private static Cacholic INSTANCE = null;
    
    private CommandLine commandLine = null;
    private Gui gui = null;
    private Language language = null;
    private EntityManagerFactory emf = null;
    private String baseFolder = null;
    
    private boolean booting = true;
    
    /**
     * Constructor hidden from public.
     * Use <code>setup</code> to initialize.
     * 
     * @param cmd Command Line arguments of the main application
     * @see Cacholic#setup(de.jued.dev.lib.libjued.CommandLine) 
     */
    private Cacholic(final CommandLine cmd)
    {
        this.commandLine = cmd;
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } 
        catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e)
        {
            Logger.getLogger(Cacholic.class.getCanonicalName()).log(Level.WARNING, "Unable to predefine System Look and Feel.", e);
        }
    }
    
    /**
     * Initialize the component.
     */
    private void initialize() throws InterruptedException, InvocationTargetException, CacholicException
    {
        // Load language
        final String langset = this.commandLine.getValue("lang", Language.DEFAULT_LANGUAGE);
        this.language = Language.factory(langset);
        // Load Home Dir
        this.baseFolder = String.format("%s%c.cacholic", System.getProperty("user.home"), File.separatorChar);
        final File f = new File(this.baseFolder);
        if (!f.exists())
        {
            if (!f.mkdirs())
            {
                throw new CacholicException(String.format("Unable to create Application Working Directory under '%s'.", f.getAbsolutePath()));
            }
        }
        if (!f.exists())
        {
            throw new CacholicException("The Application Working Directory does not exist. This is strange, because the application tried to create it.");
        }
        if (!f.isDirectory())
        {
            throw new CacholicException("The default Application Working Directory seems to be a file.");
        }
        // DB URL composing
        final String dbFile = String.format("%s%cDataBase", f.getAbsolutePath(), File.separatorChar);
        final String dbURL = String.format("%s%s", Schema.PROTOCOL, dbFile.replace(File.separatorChar, '/'));
        final File db = new File(dbFile);
        final boolean create = !db.isDirectory();
        Schema.check(dbURL, create);
        final HashMap<String, String> dbProperties = new HashMap<String, String>()
        {{
            put("javax.persistence.jdbc.driver", Schema.DRIVER);
            put("javax.persistence.jdbc.url", dbURL);
        }};
        this.emf = Persistence.createEntityManagerFactory("CacholicPU", dbProperties);
        EventMessenger.getInstance().register(Event.EVENT_BEFORE_EXIT, Closer.getInstance());
        EventMessenger.getInstance().register(Event.EVENT_EXIT, this);
        this.gui = Gui.factory();
        this.booting = false;
    }
    
    /**
     * Get the command line arguments
     * 
     * @return The command line arguments
     */
    public final CommandLine getCommandLine()
    {
        return this.commandLine;
    }
    
    /**
     * Get the application's language object
     * 
     * @return Application Language Object
     */
    public final Language getLang()
    {
        return this.language;
    }
    
    /**
     * Get the EntityManagerFactory
     * 
     * @return The current entity manager factory
     */
    public final EntityManagerFactory getEntityManagerFactory()
    {
        return this.emf;
    }
    
    /**
     * Get the gui instance
     * 
     * @return The GUI instance
     */
    public final Gui getGui()
    {
        return this.gui;
    }
    
    /**
     * Launch the application
     */
    protected void launch() throws CacholicException
    {
        this.gui.launch();
    }
    
    /**
     * Get the instance of the Cacholic with the option to override the boot flag.
     * 
     * @param overrideBoot Override the boot flag
     * @return The instance of the Cacholic
     */
    public static Cacholic getInstance(final boolean overrideBoot)
    {
        if (null == Cacholic.INSTANCE)
        {
            return null;
        }
        if ((Cacholic.INSTANCE.booting) && (!overrideBoot))
        {
            return null;
        }
        return Cacholic.INSTANCE;        
    }
    
    /**
     * The <code>Cacholic</code> is something like a singleton. Here comes the
     * instance.
     * 
     * @return The instance or <code>NULL</code> if not initialized.
     */
    public static Cacholic getInstance()
    {
        return Cacholic.getInstance(false);
    }

    /**
     * Setup the Application
     * 
     * @param cmd Command Line Arguments
     * @return The initialized Cacholic App Object.
     */
    protected static Cacholic setup(final CommandLine cmd) throws InterruptedException, InvocationTargetException, CacholicException
    {
        if (null != Cacholic.INSTANCE)
        {
            return Cacholic.getInstance();
        }
        Cacholic.INSTANCE = new Cacholic(cmd);
        Cacholic.INSTANCE.initialize();
        return Cacholic.INSTANCE;
    }

    @Override
    public void onEvent(Event evt)
    {
        if (evt.getEventType() == Event.EVENT_EXIT)
        {
            System.exit(0);
        }
    }
    
}
