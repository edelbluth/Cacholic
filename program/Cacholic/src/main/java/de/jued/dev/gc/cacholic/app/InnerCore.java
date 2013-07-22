package de.jued.dev.gc.cacholic.app;

import javax.swing.JPanel;

/**
 * This is the inner core implementation
 * 
 * @author Juergen Edelbluth <dev@jued.de>
 * @since 0.1
 * @version 0.1
 */
public class InnerCore extends JPanel
{
    
    /**
     * Hidden constructor - use factory method!
     */
    private InnerCore()
    {
        super();
    }
    
    /**
     * Initialize the inner core instance
     */
    private void initialize()
    {
        
    }
    
    /**
     * Create a new instance
     * 
     * @return a new inner core
     */
    public static InnerCore factory()
    {
        final InnerCore s = new InnerCore();
        s.initialize();
        return s;
    }
    
}
