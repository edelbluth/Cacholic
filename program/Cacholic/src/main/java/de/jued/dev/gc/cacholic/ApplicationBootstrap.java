package de.jued.dev.gc.cacholic;

import de.jued.dev.lib.libjued.CommandLine;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Cacholic Application is an easy to use Geocaching database.
 * 
 * @author Juergen Edelbluth <dev@jued.de>
 * @version 0.1
 * @since 0.1
 */
public class ApplicationBootstrap 
{
    
    /**
     * The standard main method. This is where the program starts.
     * 
     * @param args Command Line Arguments
     */
    public static void main(final String[] args)
    {
        final CommandLine cmd = CommandLine.factory(args);
        try 
        {
            Cacholic.setup(cmd).launch();
        }
        catch (InterruptedException | InvocationTargetException ex)
        {
            Logger.getLogger(ApplicationBootstrap.class.getCanonicalName()).log(Level.SEVERE, "Unable to launch application.", ex);
            System.exit(-1);
        }
    }
    
}
