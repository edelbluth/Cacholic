package de.jued.dev.gc.cacholic;

import de.jued.dev.lib.libjued.CommandLine;

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
    private void initialize()
    {
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
     * Launch the application
     */
    protected void launch()
    {
    }
    
    /**
     * The <code>Cacholic</code> is something like a singleton. Here comes the
     * instance.
     * 
     * @return The instance or <code>NULL</code> if not initialized.
     */
    public static Cacholic getInstance()
    {
        if (null == Cacholic.INSTANCE)
        {
            return null;
        }
        if (Cacholic.INSTANCE.booting)
        {
            return null;
        }
        return Cacholic.INSTANCE;
    }

    /**
     * Setup the Application
     * 
     * @param cmd Command Line Arguments
     * @return The initialized Cacholic App Object.
     */
    protected static Cacholic setup(final CommandLine cmd)
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
