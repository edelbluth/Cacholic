package de.jued.dev.gc.cacholic.db.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.Table;

/**
 * Entity class for the Cache User Flag
 * 
 * @author Juergen Edelbluth <dev@jued.de>
 * @version 0.1
 * @since 0.1
 */
@Entity @Table(name = "CACHE_USER_FLAG")
@NamedQueries({
})
public class CacheUserFlag implements Serializable
{
    
    @Id @Column(name = "SYS_UUID", insertable = true, nullable = false, updatable = false, length = 16) private byte[] uuid;
    @Column(name = "FLAG_NAME", insertable = true, nullable = false, unique = true, length = 255) private String name;
    @Lob @Column(name = "FLAG_DESCIPTION", insertable = true, length = (1 * 1024 * 1024) - 1, nullable = true) private String description;

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

    public String getDescription() 
    {
        return description;
    }

    public void setDescription(String description) 
    {
        this.description = description;
    }

}
