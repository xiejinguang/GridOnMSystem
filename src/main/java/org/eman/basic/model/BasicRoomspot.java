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
    @NamedQuery(name = "BasicRoomspot.findAll", query = "SELECT b FROM BasicRoomspot b"),
    @NamedQuery(name = "BasicRoomspot.findByRoomID", query = "SELECT b FROM BasicRoomspot b WHERE b.roomID = :roomID"),
    @NamedQuery(name = "BasicRoomspot.findByRoomCode", query = "SELECT b FROM BasicRoomspot b WHERE b.roomCode = :roomCode"),
    @NamedQuery(name = "BasicRoomspot.findByRoomName", query = "SELECT b FROM BasicRoomspot b WHERE b.roomName = :roomName"),
    @NamedQuery(name = "BasicRoomspot.findByProvince", query = "SELECT b FROM BasicRoomspot b WHERE b.province = :province"),
    @NamedQuery(name = "BasicRoomspot.findByCity", query = "SELECT b FROM BasicRoomspot b WHERE b.city = :city"),
    @NamedQuery(name = "BasicRoomspot.findByCounty", query = "SELECT b FROM BasicRoomspot b WHERE b.county = :county"),
    @NamedQuery(name = "BasicRoomspot.findByGrid", query = "SELECT b FROM BasicRoomspot b WHERE b.grid = :grid"),
    @NamedQuery(name = "BasicRoomspot.findByAddress", query = "SELECT b FROM BasicRoomspot b WHERE b.address = :address"),
    @NamedQuery(name = "BasicRoomspot.findByStatus", query = "SELECT b FROM BasicRoomspot b WHERE b.status = :status")})
public class BasicRoomspot implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(nullable = false, length = 36)
    private String roomID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(nullable = false, length = 20)
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "roomID")
    private Collection<BasicStation> basicStationCollection;

    public BasicRoomspot() {
    }

    public BasicRoomspot(String roomID) {
        this.roomID = roomID;
    }

    public BasicRoomspot(String roomID, String roomCode, String roomName, String province, String city, String county, String grid) {
        this.roomID = roomID;
        this.roomCode = roomCode;
        this.roomName = roomName;
        this.province = province;
        this.city = city;
        this.county = county;
        this.grid = grid;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
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
    public Collection<BasicStation> getBasicStationCollection() {
        return basicStationCollection;
    }

    public void setBasicStationCollection(Collection<BasicStation> basicStationCollection) {
        this.basicStationCollection = basicStationCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roomID != null ? roomID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BasicRoomspot)) {
            return false;
        }
        BasicRoomspot other = (BasicRoomspot) object;
        if ((this.roomID == null && other.roomID != null) || (this.roomID != null && !this.roomID.equals(other.roomID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.eman.basic.model.BasicRoomspot[ roomID=" + roomID + " ]";
    }
    
}
