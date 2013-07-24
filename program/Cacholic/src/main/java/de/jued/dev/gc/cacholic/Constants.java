package de.jued.dev.gc.cacholic;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Building Constants for the program
 * 
 * @author Juergen Edelbluth <dev@jued.de>
 * @version 0.1
 * @since 1.0
 */
public final class Constants
{
    
    public static final String VERSION = "0.1";
    public static String BUILD = "0";
    
    static
    {
        final Properties p = new Properties();
        try
        {
            p.load(Constants.class.getResourceAsStream("version.properties"));
            BUILD = p.getProperty("buildNumber", "-1");
        }
        catch (IOException | NullPointerException ex)
        {
            BUILD = String.format("Error (%s)", ex.getMessage());
        }
    }
    
}
