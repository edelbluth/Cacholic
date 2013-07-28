package de.jued.dev.gc.cacholic.db.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity for a GPX Import
 * 
 * @author Juergen Edelbluth <dev@jued.de>
 * @version 0.1
 * @since 0.1
 */
@Entity @Table(name = "GPX_IMPORT")
@NamedQueries({
    @NamedQuery(name = "GpxImport.findByUUID", query = "select g from GpxImport g where g.uuid=:uuid")
})
public class GpxImport implements Serializable
{
    
    @Id @Column(name = "SYS_UUID", insertable = true, nullable = false, updatable = false, length = 16) private byte[] uuid;
    @Temporal(TemporalType.TIMESTAMP) @Column(name = "IMPORTED", insertable = true, nullable = false, updatable = true) private Date timestamp;
    @Column(name = "FILENAME", insertable = true, nullable = false, updatable = true, length = 1023) private String filename;
    @Column(name = "GPX_NAME", insertable = true, nullable = true, updatable = true, length = 1023) private String gpxName;
    @Temporal(TemporalType.TIMESTAMP) @Column(name = "GPX_TIMESTAMP", insertable = true, nullable = true, updatable = true) private Date gpxTimestamp;
    @Column(name = "MINLAT", insertable = true, nullable = true, updatable = true) private double minLatitude;
    @Column(name = "MAXLAT", insertable = true, nullable = true, updatable = true) private double maxLatitude;
    @Column(name = "MINLON", insertable = true, nullable = true, updatable = true) private double minLongitude;
    @Column(name = "MAXLON", insertable = true, nullable = true, updatable = true) private double maxLongitude;
    @Column(name = "IMPORTED_ITEMS", insertable = true, nullable = false, updatable = true) private long itemCount;
    @Lob @Column(name = "IMPORT_LOG", insertable = true, nullable = true, updatable = true, length = (5 * 1024 *1024) - 1) private String log;

    public byte[] getUuid()
    {
        return uuid;
    }

    public void setUuid(byte[] uuid)
    {
        this.uuid = uuid;
    }

    public Date getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) 
    {
        this.timestamp = timestamp;
    }

    public String getFilename()
    {
        return filename;
    }

    public void setFilename(String filename)
    {
        this.filename = filename;
    }

    public String getGpxName()
    {
        return gpxName;
    }

    public void setGpxName(String gpxName) 
    {
        this.gpxName = gpxName;
    }

    public Date getGpxTimestamp()
    {
        return gpxTimestamp;
    }

    public void setGpxTimestamp(Date gpxTimestamp)
    {
        this.gpxTimestamp = gpxTimestamp;
    }

    public double getMinLatitude()
    {
        return minLatitude;
    }

    public void setMinLatitude(double minLatitude)
    {
        this.minLatitude = minLatitude;
    }

    public double getMaxLatitude()
    {
        return maxLatitude;
    }

    public void setMaxLatitude(double maxLatitude)
    {
        this.maxLatitude = maxLatitude;
    }

    public double getMinLongitude()
    {
        return minLongitude;
    }

    public void setMinLongitude(double minLongitude)
    {
        this.minLongitude = minLongitude;
    }

    public double getMaxLongitude()
    {
        return maxLongitude;
    }

    public void setMaxLongitude(double maxLongitude) 
    {
        this.maxLongitude = maxLongitude;
    }

    public long getItemCount()
    {
        return itemCount;
    }

    public void setItemCount(long itemCount) 
    {
        this.itemCount = itemCount;
    }

    public String getLog() 
    {
        return log;
    }

    public void setLog(String log)
    {
        this.log = log;
    }
    
}
