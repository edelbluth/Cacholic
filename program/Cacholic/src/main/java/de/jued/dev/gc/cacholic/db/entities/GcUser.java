package de.jued.dev.gc.cacholic.db.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Entity for a geocaching.com user
 * 
 * @author Juergen Edelbluth <dev@jued.de>
 * @version 0.1
 * @since 0.1
 */
@Entity @Table(name = "GCUSER")
@NamedQueries({
    @NamedQuery(name = "GcUser.findByUUID", query = "select g from GcUser g where g.uuid=:uuid")
})
public class GcUser implements Serializable
{
    
    @Id @Column(name = "SYS_UUID", insertable = true, nullable = false, updatable = false, length = 16) private byte[] uuid;
    @Column(name = "GC_USER_ID", insertable = true, nullable = false, updatable = false, unique = true) private long gcUserID;
    @Column(name = "GC_USERNAME", insertable = true, nullable = false, updatable = true, length = 1023) private String gcUserName;

    public byte[] getUuid() 
    {
        return uuid;
    }

    public void setUuid(byte[] uuid)
    {
        this.uuid = uuid;
    }

    public long getGcUserID() 
    {
        return gcUserID;
    }

    public void setGcUserID(long gcUserID)
    {
        this.gcUserID = gcUserID;
    }

    public String getGcUserName()
    {
        return gcUserName;
    }

    public void setGcUserName(String gcUserName)
    {
        this.gcUserName = gcUserName;
    }
    
}
