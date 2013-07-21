package de.jued.dev.gc.cacholic.db.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * Database Entity for the Version Table in DB Version 1
 *  
 * @author Juergen Edelbluth <dev@jued.de>
 * @since 0.1
 * @version 0.1
 */
@Entity @Table(name = "DB_VERSION")
@NamedQueries({
    @NamedQuery(name = "Version.highToLow", query = "select v from Version v order by v.version desc")
})
public class Version implements Serializable
{
    @Id @Column(name = "VERSION_NO", insertable = true, updatable = false, unique = true) private long version;
    @Temporal(TemporalType.TIMESTAMP) @Column(name = "INSTALLED", insertable = true, updatable = false, unique = false) private Date installed;

    public long getVersion()
    {
        return version;
    }

    public void setVersion(long version)
    {
        this.version = version;
    }

    public Date getInstalled()
    {
        return installed;
    }

    public void setInstalled(Date installed)
    {
        this.installed = installed;
    }
    
}
