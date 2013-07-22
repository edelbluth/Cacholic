package de.jued.dev.gc.cacholic.app;

import de.jued.dev.gc.cacholic.Cacholic;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * The main menu bar implementation for the GUI
 * 
 * @author Juergen Edelbluth <dev@jued.de>
 * @since 0.1
 * @version 0.1
 */
public class MainMenu extends JMenuBar implements ActionListener
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
        final JMenu file = new JMenu(Cacholic.getInstance(true).getLang().get("gc.menu.file"));
        file.addSeparator();
        final JMenuItem file_quit = new JMenuItem(Cacholic.getInstance(true).getLang().get("gc.menu.file.quit"));
        file_quit.setActionCommand("file.quit");
        file_quit.addActionListener(this);
        file.add(file_quit);
        this.add(file);
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

    @Override
    public void actionPerformed(ActionEvent ae)
    {
        if (null == ae)
        {
            return;
        }
        final String actionCommand = ae.getActionCommand();
        if (null == actionCommand)
        {
            return;
        }
        action:
        {
            if (actionCommand.equalsIgnoreCase("file.quit"))
            {
                Cacholic.getInstance().getGui().quit();
                break action;
            }
            Logger.getLogger(MainMenu.class.getCanonicalName()).log(Level.WARNING, String.format("Unknown action command triggered: %s.", actionCommand));
        }
    }
    
}
