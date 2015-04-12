/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.eman.basic.model;

import org.eman.gmsys.model.FixDemand;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.peasant.jpa.Labeled;

/**
 *
 * @author 谢金光
 */
@Entity
@Table(catalog = "jobpromotion", schema = "",name = "basic_station", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"name"}),
    @UniqueConstraint(columnNames = {"statCode"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Station.findAll", query = "SELECT s FROM Station s"),
    @NamedQuery(name = "Station.findById", query = "SELECT s FROM Station s WHERE s.id = :id"),
    @NamedQuery(name = "Station.findByStatCode", query = "SELECT s FROM Station s WHERE s.statCode = :statCode"),
    @NamedQuery(name = "Station.findByName", query = "SELECT s FROM Station s WHERE s.name = :name"),
    @NamedQuery(name = "Station.findByAddress", query = "SELECT s FROM Station s WHERE s.address = :address"),
    @NamedQuery(name = "Station.findByOwner", query = "SELECT s FROM Station s WHERE s.owner = :owner"),
    @NamedQuery(name = "Station.findByType", query = "SELECT s FROM Station s WHERE s.type = :type"),
    @NamedQuery(name = "Station.findByAccomMaintainer", query = "SELECT s FROM Station s WHERE s.accomMaintainer = :accomMaintainer"),
    @NamedQuery(name = "Station.findByStatus", query = "SELECT s FROM Station s WHERE s.status = :status")})
public class Station implements Serializable,Labeled {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(nullable = false, length = 36)
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(nullable = false, length = 20)
    private String statCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(nullable = false, length = 20)
    private String name;
    @Size(max = 45)
    @Column(length = 45)
    private String address;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String owner;
    @Size(max = 45)
    @Column(length = 45)
    private String type;
    @Size(max = 45)
    @Column(length = 45)
    private String accomMaintainer;
    @Size(max = 45)
    @Column(length = 45)
    private String status;
    @Lob
    @Size(max = 65535)
    @Column(length = 65535)
    private String commont;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stationId")
    private Collection<FixDemand> fixDemandCollection;

    @JoinColumn(name = "roomspotId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false,cascade = {CascadeType.MERGE,})
    private Roomspot roomspot;

    public Station() {
    }

    public Station(String id) {
        this.id = id;
    }

    public Station(String id, String statCode, String name, String owner) {
        this.id = id;
        this.statCode = statCode;
        this.name = name;
        this.owner = owner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatCode() {
        return statCode;
    }

    public void setStatCode(String statCode) {
        this.statCode = statCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccomMaintainer() {
        return accomMaintainer;
    }

    public void setAccomMaintainer(String accomMaintainer) {
        this.accomMaintainer = accomMaintainer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCommont() {
        return commont;
    }

    public void setCommont(String commont) {
        this.commont = commont;
    }

    @XmlTransient
    public Collection<FixDemand> getFixDemandCollection() {
        return fixDemandCollection;
    }

    public void setFixDemandCollection(Collection<FixDemand> fixDemandCollection) {
        this.fixDemandCollection = fixDemandCollection;
    }



    public Roomspot getRoomspot() {
        return roomspot;
    }

    public void setRoomspot(Roomspot roomspot) {
        this.roomspot = roomspot;
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
        if (!(object instanceof Station)) {
            return false;
        }
        Station other = (Station) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.eman.basic.model.Station[ id=" + id + " ]";
    }

    @Override
    public String getLabel() {
        return statCode+"|"+name;
    }
    
}
