package de.jued.dev.gc.cacholic.app;

import de.jued.dev.gc.cacholic.Cacholic;
import de.jued.dev.gc.cacholic.CacholicException;
import de.jued.dev.gc.cacholic.Constants;
import de.jued.dev.gc.cacholic.Event;
import de.jued.dev.gc.cacholic.EventMessenger;
import de.jued.dev.gc.cacholic.db.FastPath;
import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * Graphical User interface main class
 * 
 * @author Juergen Edelbluth <dev@jued.de>
 * @version 0.1
 * @since 0.1
 */
public class Gui extends JFrame implements WindowListener
{
    
    private MainMenu mainMenu = null;
    private StatusBar statusBar = null;
    private ToolBar toolBar = null;
    private InnerCore innerCore = null;
    
    private Gui()
    {
        super(String.format("%s V%s", Cacholic.getInstance(true).getLang().get("gc.AppName"), Constants.VERSION));
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
    
    private void initialize() throws InterruptedException, InvocationTargetException
    {
        final Gui me = this;
        SwingUtilities.invokeAndWait(new Runnable()
        {
            @Override
            public void run()
            {
                me.setLayout(new BorderLayout(0, 0));
                me.mainMenu = MainMenu.factory();
                me.setJMenuBar(me.mainMenu);
                me.statusBar = StatusBar.factory();
                me.add(me.statusBar, BorderLayout.SOUTH);
                me.toolBar = ToolBar.factory();
                me.add(me.toolBar, BorderLayout.NORTH);
                me.innerCore = InnerCore.factory();
                me.add(me.innerCore, BorderLayout.CENTER);
                me.pack();
            }
        });
        this.addWindowListener(this);
        this.setLocationRelativeTo(this);
    }
    
    public void launch() throws CacholicException
    {
        this.setTitle(String.format("%s | DBV: %d", this.getTitle(), FastPath.getDatabaseVersion()));
        this.setVisible(true);        
    }
    
    public static Gui factory() throws InterruptedException, InvocationTargetException
    {
        final Gui gui = new Gui();
        gui.initialize();
        return gui;
    }
    
    /**
     * Exit the program.
     */
    private void exit()
    {
        this.dispose();
        EventMessenger.getInstance().fireEvent(Event.synchronousEventFactory(Event.EVENT_BEFORE_EXIT, null));
        EventMessenger.getInstance().fireEvent(Event.asynchronousEventFactory(Event.EVENT_EXIT, null));        
    }
    
    /**
     * Ask the user if he/she really wants to quit.
     */
    protected void quit()
    {
        final String title = Cacholic.getInstance().getLang().get("gc.closing.title");
        final String message = Cacholic.getInstance().getLang().get("gc.closing.message");
        final int answer = JOptionPane.showConfirmDialog(this, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (answer == JOptionPane.YES_OPTION)
        {
            this.exit();
        }        
    }

    @Override
    public void windowOpened(WindowEvent we)
    {
    }

    @Override
    public void windowClosing(WindowEvent we)
    {
        this.quit();
    }

    @Override
    public void windowClosed(WindowEvent we)
    {
    }

    @Override
    public void windowIconified(WindowEvent we)
    {
    }

    @Override
    public void windowDeiconified(WindowEvent we)
    {
    }

    @Override
    public void windowActivated(WindowEvent we)
    {
    }

    @Override
    public void windowDeactivated(WindowEvent we)
    {
    }
    
}
