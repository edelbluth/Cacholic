package de.jued.dev.gc.cacholic.db.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The Cache Symbol Entity
 * 
 * @author juergen
 * @since 0.1
 * @version 0.1
 */
@Entity @Table(name = "CACHESYMBOL")
@NamedQueries({
    @NamedQuery(name = "CacheSymbol.findByUUID", query = "select c from CacheSymbol c where c.uuid=:uuid")
})
public class CacheSymbol implements Serializable
{

    @Id @Column(name = "SYS_UUID", insertable = true, nullable = false, updatable = false, length = 16) private byte[] uuid;
    @Column(name = "SYMBOL_NAME", insertable = true, updatable = false, nullable = false, unique = true, length = 255) private String name;
    @Lob @Column(name = "IMG_DATA", insertable = true, updatable = true, nullable = true, length = (255 * 1024) - 1) private byte[] data;

    public byte[] getUuid()
    {
        return uuid;
    }

    public void setUuid(byte[] uuid) 
    {
        this.uuid = uuid;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public byte[] getData()
    {
        return data;
    }

    public void setData(byte[] data)
    {
        this.data = data;
    }
    
}
