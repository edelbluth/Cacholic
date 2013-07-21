package de.jued.dev.gc.cacholic;

import de.jued.dev.lib.libjued.Exceptioneer;

/**
 * General Exception for Sh...
 * 
 * @author Juergen Edelbluth <dev@jued.de>
 * @version 0.1
 * @since 0.1
 */
public class CacholicException extends Exception
{
    
    public CacholicException(final String message)
    {
        super(message);
    }
    
    public CacholicException()
    {
        this("No exception message provided");
    }
    
    public CacholicException(final String message, final Throwable ex)
    {
        this(null != ex ? String.format("Message: %s\nNested Exception: %s\nNested Message: %s\nNested Stacktrace: %s", message, ex.getClass().getCanonicalName(), ex.getMessage(), Exceptioneer.formatStackTrace(ex.getStackTrace())) : message);
    }
    
}
