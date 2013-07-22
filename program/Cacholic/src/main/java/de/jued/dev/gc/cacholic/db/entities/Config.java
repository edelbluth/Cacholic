package de.jued.dev.gc.cacholic.db.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;


/**
 * Database Entity for the Configuration Table in DB Version 1
 *  
 * @author Juergen Edelbluth <dev@jued.de>
 * @since 0.1
 * @version 0.1
 */
@Entity @Table(name = "GC_CONFIGURATION")
@NamedQueries({
    @NamedQuery(name = "Config.getByKey", query = "select c from Config c where c.key=:key")
})
public class Config implements Serializable
{
    @Id @Column(name = "CONF_KEY", insertable = true, updatable = true, unique = true, length = 1023) private String key;
    @Lob @Column(name = "CONF_VALUE", insertable = true, updatable = true, unique = false) private Serializable value;
    
    @Version @Column(name = "CONF_VERSION") private long version;

    public String getKey()
    {
        return key;
    }

    public void setKey(String key) 
    {
        this.key = key;
    }

    public Serializable getValue() 
    {
        return value;
    }

    public void setValue(Serializable value)
    {
        this.value = value;
    }

    public long getVersion()
    {
        return version;
    }

    public void setVersion(long version) 
    {
        this.version = version;
    }
    
}
