package de.jued.dev.gc.cacholic.app;

import javax.swing.JPanel;

/**
 * This is the tool bar implementation
 * 
 * @author Juergen Edelbluth <dev@jued.de>
 * @since 0.1
 * @version 0.1
 */
public class ToolBar extends JPanel
{
    
    /**
     * Hidden constructor - use factory method!
     */
    private ToolBar()
    {
        super();
    }
    
    /**
     * Initialize the tool bar instance
     */
    private void initialize()
    {
        
    }
    
    /**
     * Create a new instance
     * 
     * @return a new tool bar
     */
    public static ToolBar factory()
    {
        final ToolBar s = new ToolBar();
        s.initialize();
        return s;
    }
    
}
