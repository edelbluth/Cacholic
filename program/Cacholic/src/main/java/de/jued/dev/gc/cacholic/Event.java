package de.jued.dev.gc.cacholic;

import java.io.Serializable;

/**
 * Event definition
 * 
 * @author Juergen Edelbluth <dev@jued.de>
 * @since 0.1
 * @version 0.1
 */
public class Event implements Serializable
{

    public static final int EVENT_BEFORE_EXIT = 2;
    public static final int EVENT_EXIT = 1;
    
    private boolean asynchronous = false;
    private Serializable message = null;
    
    private int eventType = 0;
    
    /**
     * Hidden constructor - use factory!
     * 
     * @param eventType type of the event
     * @param async async mode
     * @param message data to be transmitted
     */
    private Event(final int eventType, final boolean async, final Serializable message)
    {
        this.eventType = eventType;
        this.asynchronous = async;
        this.message = message;
    }

    /**
     * Is the event asynchronous?
     * 
     * @return asynchronous state
     */
    public boolean isAsynchronous()
    {
        return asynchronous;
    }

    /**
     * Get the message data
     * 
     * @return the message data
     */
    public Serializable getMessage() 
    {
        return message;
    }

    /**
     * Get the event type
     * 
     * @return the event type
     */
    public int getEventType()
    {
        return eventType;
    }
    
    /**
     * Create an asynchronous event
     * 
     * @param eventType event type
     * @param obj message data 
     * @return the event
     */
    public static Event asynchronousEventFactory(final int eventType, final Serializable obj)
    {
        return new Event(eventType, true, obj);
    }
    
    /**
     * Create an synchronous event
     * 
     * @param eventType event type
     * @param obj message data 
     * @return the event
     */
    public static Event synchronousEventFactory(final int eventType, final Serializable obj)
    {
        return new Event(eventType, false, obj);
    }

}
