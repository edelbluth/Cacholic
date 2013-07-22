package de.jued.dev.gc.cacholic.app;

import javax.swing.JMenuBar;

/**
 * The main menu bar implementation for the GUI
 * 
 * @author Juergen Edelbluth <dev@jued.de>
 * @since 0.1
 * @version 0.1
 */
public class MainMenu extends JMenuBar
{
    
    /**
     * Hidden constructor - use <code>factory</code>
     * 
     * @see MainMenu#factory() 
     */
    private MainMenu()
    {
        super();
    }
    
    /**
     * Initialize the component
     */
    private void initialize()
    {
        
    }
    
    /**
     * Create a new MainMenu
     * 
     * @return the new MainMenu
     */
    public static MainMenu factory()
    {
        final MainMenu m = new MainMenu();
        m.initialize();
        return m;
    }
    
}
