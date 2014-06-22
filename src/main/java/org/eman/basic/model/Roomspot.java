/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eman.basic.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author 谢金光
 */
@Entity
@Table(name = "basic_roomspot", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"roomCode"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Roomspot.findAll", query = "SELECT r FROM Roomspot r"),
    @NamedQuery(name = "Roomspot.findById", query = "SELECT r FROM Roomspot r WHERE r.id = :id"),
    @NamedQuery(name = "Roomspot.findByRoomCode", query = "SELECT r FROM Roomspot r WHERE r.roomCode = :roomCode"),
    @NamedQuery(name = "Roomspot.findByRoomName", query = "SELECT r FROM Roomspot r WHERE r.roomName = :roomName"),
    @NamedQuery(name = "Roomspot.findByProvince", query = "SELECT r FROM Roomspot r WHERE r.province = :province"),
    @NamedQuery(name = "Roomspot.findByCity", query = "SELECT r FROM Roomspot r WHERE r.city = :city"),
    @NamedQuery(name = "Roomspot.findByCounty", query = "SELECT r FROM Roomspot r WHERE r.county = :county"),
    @NamedQuery(name = "Roomspot.findByGrid", query = "SELECT r FROM Roomspot r WHERE r.grid = :grid"),
    @NamedQuery(name = "Roomspot.findByPropertyOwner", query = "SELECT r FROM Roomspot r WHERE r.propertyOwner = :propertyOwner"),
    @NamedQuery(name = "Roomspot.findByAddress", query = "SELECT r FROM Roomspot r WHERE r.address = :address"),
    @NamedQuery(name = "Roomspot.findByStatus", query = "SELECT r FROM Roomspot r WHERE r.status = :status")})
public class Roomspot implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(nullable = false, length = 36)
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String roomCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String roomName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String province;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String city;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String county;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String grid;
    @Basic(optional = false)
    @NotNull
    @Size(max = 45)
    @Column(nullable = false, length = 45)
    private String propertyOwner;
    @Size(max = 255)
    @Column(length = 255)
    private String address;
    @Lob
    @Size(max = 65535)
    @Column(length = 65535)
    private String commont;
    @Size(max = 45)
    @Column(length = 45)
    private String status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "roomspotId")
    private Collection<Station> stationCollection;

    public Roomspot() {
    }

    public Roomspot(String id) {
        this.id = id;
    }

    public Roomspot(String id, String roomCode, String roomName, String province, String city, String county, String grid) {
        this.id = id;
        this.roomCode = roomCode;
        this.roomName = roomName;
        this.province = province;
        this.city = city;
        this.county = county;
        this.grid = grid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getGrid() {
        return grid;
    }

    public void setGrid(String grid) {
        this.grid = grid;
    }

    public String getPropertyOwner() {
        return propertyOwner;
    }

    public void setPropertyOwner(String propertyOwner) {
        this.propertyOwner = propertyOwner;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCommont() {
        return commont;
    }

    public void setCommont(String commont) {
        this.commont = commont;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @XmlTransient
    public Collection<Station> getStationCollection() {
        return stationCollection;
    }

    public void setStationCollection(Collection<Station> stationCollection) {
        this.stationCollection = stationCollection;
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
        if (!(object instanceof Roomspot)) {
            return false;
        }
        Roomspot other = (Roomspot) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.eman.basic.model.Roomspot[ id=" + id + " ]";
    }

}
