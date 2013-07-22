package de.jued.dev.gc.cacholic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handle the events within the application
 * 
 * @author Juergen Edelbluth
 * @since 0.1
 * @version 0.1
 */
public class EventMessenger
{

    private static EventMessenger INSTANCE = null;
    
    private ConcurrentHashMap<Integer, CopyOnWriteArrayList<EventListener>> registry = null;
    private CopyOnWriteArrayList<EventListener> allEventsRegistry = null;
    
    /**
     * Hidden constructor - use singleton access method!
     */
    private EventMessenger()
    {
        this.registry = new ConcurrentHashMap<>();
        this.allEventsRegistry = new CopyOnWriteArrayList<>();
    }
    
    /**
     * Fire an event
     * 
     * @param e The event
     */
    public void fireEvent(final Event e)
    {
        final ArrayList<Thread> threads = new ArrayList<>();
        final ArrayList<EventListener> toBeInformed = new ArrayList<>();
        final CopyOnWriteArrayList<EventListener> listeners = this.registry.get(e.getEventType());
        if ((null != listeners) && (listeners.size() > 0))
        {
            toBeInformed.addAll(listeners);
        }
        if (this.allEventsRegistry.size() > 0)
        {
            toBeInformed.addAll(this.allEventsRegistry);
        }
        for (final Iterator<EventListener> it = toBeInformed.iterator(); it.hasNext(); )
        {
            final EventListener el = it.next();
            if (null == el)
            {
                continue;
            }
            final Thread thread = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    el.onEvent(e);
                }
            });
            thread.setDaemon(true);
            threads.add(thread);
            thread.start();
        }
        if (!e.isAsynchronous())
        {
            for (final Iterator<Thread> it = threads.iterator(); it.hasNext(); )
            {
                try
                {
                    it.next().join();
                }
                catch (InterruptedException | NullPointerException ex)
                {
                    Logger.getLogger(EventMessenger.class.getCanonicalName()).log(Level.WARNING, "Unable to join thread", ex);
                }
            }
        }
    }
    
    /**
     * Register for reveiving events
     * 
     * @param eventType The event type
     * @param listener The listener implementation class
     */
    public void register(final int eventType, final EventListener listener)
    {
        if (null == listener)
        {
            return;
        }
        if (!this.registry.containsKey(eventType))
        {
            this.registry.put(eventType, new CopyOnWriteArrayList<EventListener>());
        }
        this.registry.get(eventType).add(listener);
    }
    
    /**
     * Register for all events
     * 
     * @param listener the listener implementation class
     */
    public void register(final EventListener listener)
    {
        if (null == listener)
        {
            return;
        }
        this.allEventsRegistry.add(listener);
    }
    
    /**
     * Get the singleton instance of the EventMessenger
     * 
     * @return the EventMessenger instance
     */
    public static EventMessenger getInstance()
    {
        if (null == EventMessenger.INSTANCE)
        {
            EventMessenger.INSTANCE = new EventMessenger();
        }
        return EventMessenger.INSTANCE;
    }
    
}
