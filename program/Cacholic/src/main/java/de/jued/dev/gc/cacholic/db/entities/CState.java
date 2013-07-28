package de.jued.dev.gc.cacholic.db.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * State entity
 * 
 * @author Juergen Edelbluth <dev@jued.de>
 * @version 0.1
 * @since 0.1
 */
@Entity @Table(name = "CSTATE")
@NamedQueries({
    @NamedQuery(name = "CState.findByUUID", query = "select c from CState c where c.uuid=:uuid")
})
public class CState implements Serializable
{

    @Id @Column(name = "SYS_UUID", insertable = true, nullable = false, updatable = false, length = 16) private byte[] uuid;
    @Column(name = "STATE_NAME", nullable = false, insertable = true, updatable = true, length = 255, unique = true) private String name;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Country.class) @JoinColumn(name = "COUNTRY") private Country country;

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

    public Country getCountry() 
    {
        return country;
    }

    public void setCountry(Country country)
    {
        this.country = country;
    }
    
}
