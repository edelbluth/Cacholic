package de.jued.dev.gc.cacholic.db;

import de.jued.dev.gc.cacholic.Cacholic;
import de.jued.dev.gc.cacholic.CacholicException;
import de.jued.dev.gc.cacholic.db.entities.Version;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * Get some database data directly. This is a fast path to some important
 * information.
 * 
 * @author Juergen Edelbluth <dev@jued.de>
 * @since 0.1
 * @version 0.1
 */
public class FastPath
{
    
    /**
     * Get the database Version
     * 
     * @return the database Version
     */
    public static long getDatabaseVersion() throws CacholicException
    {
        try
        {
            final EntityManager em = Cacholic.getInstance().getEntityManagerFactory().createEntityManager();
            final TypedQuery<Version> tqv = em.createNamedQuery("Version.highToLow", Version.class);
            tqv.setMaxResults(1);
            final Version result = tqv.getSingleResult();
            em.close();
            return result.getVersion();
        }
        catch (Exception ex)
        {
            throw new CacholicException("Unable to get Version Information.", ex);
        }
    }
    
}
