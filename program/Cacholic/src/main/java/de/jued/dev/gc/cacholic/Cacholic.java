package de.jued.dev.gc.cacholic;

import de.jued.dev.gc.cacholic.app.Gui;
import de.jued.dev.gc.cacholic.lang.Language;
import de.jued.dev.lib.libjued.CommandLine;
import java.lang.reflect.InvocationTargetException;

/**
 * This is the main class for the application, instrumentalized by the bootstrap.
 * 
 * @author Juergen Edelbluth <dev@jued.de>
 * @version 0.1
 * @since 0.1
 */
public class Cacholic
{
    
    private static Cacholic INSTANCE = null;
    
    private CommandLine commandLine = null;
    private Gui gui = null;
    private Language language = null;
    
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
    }
    
    /**
     * Initialize the component.
     */
    private void initialize() throws InterruptedException, InvocationTargetException
    {
        final String langset = this.commandLine.getValue("lang", Language.DEFAULT_LANGUAGE);
        this.language = Language.factory(langset);
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
     * Launch the application
     */
    protected void launch()
    {
        this.gui.setVisible(true);
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
    protected static Cacholic setup(final CommandLine cmd) throws InterruptedException, InvocationTargetException
    {
        if (null != Cacholic.INSTANCE)
        {
            return Cacholic.getInstance();
        }
        Cacholic.INSTANCE = new Cacholic(cmd);
        Cacholic.INSTANCE.initialize();
        return Cacholic.INSTANCE;
    }
    
}
