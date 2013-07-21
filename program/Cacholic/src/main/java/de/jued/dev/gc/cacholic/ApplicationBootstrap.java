package de.jued.dev.gc.cacholic;

import de.jued.dev.lib.libjued.CommandLine;

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
        Cacholic.setup(cmd).launch();
    }
    
}
