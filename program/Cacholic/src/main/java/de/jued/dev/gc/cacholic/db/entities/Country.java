package de.jued.dev.gc.cacholic.db.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Country Entity
 * 
 * @author Juergen Edelbluth <dev@jued.de>
 * @since 0.1
 * @version 0.1
 */
@Entity @Table(name = "COUNTRY")
@NamedQueries({
    @NamedQuery(name = "Country.findByUUID", query = "select c from Country c where c.uuid=:uuid")
})
public class Country implements Serializable
{
    
    @Id @Column(name = "SYS_UUID", insertable = true, nullable = false, updatable = false, length = 16) private byte[] uuid;
    @Column(name = "COUNTRY_NAME", nullable = false, insertable = true, updatable = true, length = 255, unique = true) private String name;
    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY, targetEntity = CState.class) private List<CState> states;

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

    public List<CState> getStates() 
    {
        return states;
    }

    public void setStates(List<CState> states) 
    {
        this.states = states;
    }
    
}
