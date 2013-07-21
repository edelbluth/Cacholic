package de.jued.dev.gc.cacholic.app;

import de.jued.dev.gc.cacholic.Cacholic;
import de.jued.dev.gc.cacholic.Constants;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Graphical User interface main class
 * 
 * @author Juergen Edelbluth <dev@jued.de>
 * @version 0.1
 * @since 0.1
 */
public class Gui extends JFrame
{
    
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
                me.pack();
            }
        });
        this.setLocationRelativeTo(this);
    }
    
    public static Gui factory() throws InterruptedException, InvocationTargetException
    {
        final Gui gui = new Gui();
        gui.initialize();
        return gui;
    }
    
}
