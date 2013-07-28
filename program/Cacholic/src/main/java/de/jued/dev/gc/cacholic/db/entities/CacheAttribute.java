package de.jued.dev.gc.cacholic.db.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * The Cache Symbol Entity
 * 
 * @author juergen
 * @since 0.1
 * @version 0.1
 */
@Entity @Table(name = "CACHEATTRIBUTE", uniqueConstraints = {@UniqueConstraint(columnNames = {"ATTRIBUTE_ID", "MODE"})})
@NamedQueries({
    @NamedQuery(name = "CacheAttribute.findByUUID", query = "select c from CacheAttribute c where c.uuid=:uuid")
})
public class CacheAttribute implements Serializable
{

    @Id @Column(name = "SYS_UUID", insertable = true, nullable = false, updatable = false, length = 16) private byte[] uuid;
    @Column(name = "ATTRIBUTE_ID", insertable = true, nullable = false, updatable = true) private int attributeID;
    @Column(name = "ATTRIBUTE_TEXT", insertable = true, nullable = true, updatable = true, length = 1023) private String attributeText;
    @Column(name = "MODE", insertable = true, nullable = false, updatable = true) private short mode;
    @Lob @Column(name = "IMG_DATA", insertable = true, updatable = true, nullable = true, length = (255 * 1024) - 1) private byte[] data;

    public byte[] getUuid()
    {
        return uuid;
    }

    public void setUuid(byte[] uuid)
    {
        this.uuid = uuid;
    }

    public int getAttributeID()
    {
        return attributeID;
    }

    public void setAttributeID(int attributeID)
    {
        this.attributeID = attributeID;
    }

    public String getAttributeText()
    {
        return attributeText;
    }

    public void setAttributeText(String attributeText)
    {
        this.attributeText = attributeText;
    }

    public short getMode()
    {
        return mode;
    }

    public void setMode(short mode)
    {
        this.mode = mode;
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
