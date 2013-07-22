package de.jued.dev.gc.cacholic.app;

import javax.swing.JPanel;

/**
 * This is the status bar implementation
 * 
 * @author Juergen Edelbluth <dev@jued.de>
 * @since 0.1
 * @version 0.1
 */
public class StatusBar extends JPanel
{
    
    /**
     * Hidden constructor - use factory method!
     */
    private StatusBar()
    {
        super();
    }
    
    /**
     * Initialize the status bar instance
     */
    private void initialize()
    {
        
    }
    
    /**
     * Create a new instance
     * 
     * @return a new status bar
     */
    public static StatusBar factory()
    {
        final StatusBar s = new StatusBar();
        s.initialize();
        return s;
    }
    
}
