package de.jued.dev.gc.cacholic.db.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The Geocache Meta entity
 * 
 * @author Juergen Edelbluth <dev@jued.de>
 * @since 0.1
 * @version 0.1
 */
@Entity @Table(name = "GEOCACHE")
@NamedQueries({
    @NamedQuery(name = "GeoCache.findByUUID", query = "select g from GeoCache g where g.uuid=:uuid")
})
public class GeoCache implements Serializable
{
    
    @Id @Column(name = "SYS_UUID", insertable = true, nullable = false, updatable = false, length = 16) private byte[] uuid;
    @Column(name = "GC_CODE", insertable = true, nullable = true, updatable = true, unique = true, length = 255) private String gcCode;
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = CacheUserFlag.class) @JoinTable(name = "CACHEFLAGS", joinColumns = {@JoinColumn(name = "GEOCACHE", referencedColumnName = "SYS_UUID")}, inverseJoinColumns = {@JoinColumn(name = "USER_FLAG", referencedColumnName = "SYS_UUID")}) private List<CacheUserFlag> flags;
    
    public byte[] getUuid() 
    {
        return uuid;
    }

    public void setUuid(byte[] uuid)
    {
        this.uuid = uuid;
    }

    public String getGcCode()
    {
        return gcCode;
    }

    public void setGcCode(String gcCode)
    {
        this.gcCode = gcCode;
    }

    public List<CacheUserFlag> getFlags()
    {
        return flags;
    }

    public void setFlags(List<CacheUserFlag> flags)
    {
        this.flags = flags;
    }
    
}
