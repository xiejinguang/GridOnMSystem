/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.eman.gmsys.model;

import org.peasant.jpa.UUIDEntity;
import org.eman.basic.model.Roomspot;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 谢金光
 */
@Entity
@Table(catalog = "jobpromotion", schema = "",name = "gm_station_property",uniqueConstraints = @UniqueConstraint(columnNames = {"number"}))
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StationProperty.findAll", query = "SELECT s FROM StationProperty s"),
    @NamedQuery(name = "StationProperty.findById", query = "SELECT s FROM StationProperty s WHERE s.id = :id"),
    @NamedQuery(name = "StationProperty.findByNumber", query = "SELECT s FROM StationProperty s WHERE s.number = :number"),
    @NamedQuery(name = "StationProperty.findByName", query = "SELECT s FROM StationProperty s WHERE s.name = :name"),
    @NamedQuery(name = "StationProperty.findByQuantity", query = "SELECT s FROM StationProperty s WHERE s.quantity = :quantity"),
    @NamedQuery(name = "StationProperty.findByModel", query = "SELECT s FROM StationProperty s WHERE s.model = :model"),
    @NamedQuery(name = "StationProperty.findByManufacturer", query = "SELECT s FROM StationProperty s WHERE s.manufacturer = :manufacturer")})
public class StationProperty extends UUIDEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Size(max = 45)
    @Column(length = 45)
    private String number;
    @Size(max = 45)
    @Column(length = 45)
    private String name;
    private Integer quantity;
    @Size(max = 45)
    @Column(length = 45)
    private String model;
    @Size(max = 45)
    @Column(length = 45)
    private String manufacturer;    
    
    @Column
    private Boolean taged;
    
    @Lob
    @Size(max = 65535)
    @Column(length = 65535)
    private String abnormity;
    
    @JoinColumn(name = "roomspotId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Roomspot roomspotId;

    public StationProperty() {
        super();
    }

    public StationProperty(String id) {
        this.id = id;
    }



    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getAbnormity() {
        return abnormity;
    }

    public void setAbnormity(String abnormity) {
        this.abnormity = abnormity;
    }

    public Roomspot getRoomspotId() {
        return roomspotId;
    }

    public void setRoomspotId(Roomspot roomspotId) {
        this.roomspotId = roomspotId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StationProperty)) {
            return false;
        }
        StationProperty other = (StationProperty) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.eman.gmsys.model.StationProperty[ id=" + id + " ]";
    }

    public Boolean getTaged() {
        return taged;
    }

    public void setTaged(Boolean taged) {
        this.taged = taged;
    }
    
}
