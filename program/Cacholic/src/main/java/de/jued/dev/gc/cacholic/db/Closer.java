package de.jued.dev.gc.cacholic.db;

import de.jued.dev.gc.cacholic.Cacholic;
import de.jued.dev.gc.cacholic.CacholicException;
import de.jued.dev.gc.cacholic.Event;
import de.jued.dev.gc.cacholic.EventListener;
import de.jued.dev.gc.cacholic.db.schema.Schema;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class makes closing of the database easy.
 * 
 * @author Juergen Edelbluth <dev@jued.de>
 * @since 0.1
 * @version 0.1
 */
public class Closer implements EventListener
{

    private static Closer INSTANCE = null;
    
    /**
     * Hidden constructor - use singleton!
     */
    private Closer()
    {
    }
    
    @Override
    public void onEvent(Event evt)
    {
        if ((null == evt) || (evt.getEventType() != Event.EVENT_BEFORE_EXIT))
        {
            return;
        }
        Cacholic.getInstance().getEntityManagerFactory().close();
        try
        {
            Schema.shutdown();
            Logger.getLogger(Closer.class.getCanonicalName()).log(Level.INFO, "Database Shutdown completed.");
        }
        catch (CacholicException ex)
        {
            Logger.getLogger(Closer.class.getCanonicalName()).log(Level.WARNING, "Unable to close database connection.", ex);
        }
    }
    
    /**
     * Get the instance of the database closer
     * 
     * @return the instance
     */
    public static Closer getInstance()
    {
        if (null == Closer.INSTANCE)
        {
            Closer.INSTANCE = new Closer();
        }
        return Closer.INSTANCE;
    }
    
}
