package de.jued.dev.gc.cacholic;

/**
 * This must be implemented in order to receive events
 * 
 * @author Juergen Edelbluth
 * @since 0.1
 * @version 0.1
 */
public interface EventListener
{
    
    /**
     * This method is triggered on registered classes when an event appears
     * 
     * @param evt The event
     */
    public void onEvent(Event evt);
    
}
