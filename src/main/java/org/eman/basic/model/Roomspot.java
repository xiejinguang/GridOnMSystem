/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eman.basic.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.eman.gmsys.model.StationProperty;
import org.peasant.basic.model.Address;
import org.peasant.jpa.DatedEntity;
import org.peasant.jpa.Labeled;
import org.peasant.jpa.UUIDEntity;

/**
 *
 * @author 谢金光
 */
@Entity
@Table(name = "basic_roomspot", uniqueConstraints = {
    @UniqueConstraint(name="UNQ_basic_roomspot_0",columnNames = {"roomCode"}),
    @UniqueConstraint(name="UNQ_basic_roomspot_1",columnNames = {"secondName"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Roomspot.findAll", query = "SELECT r FROM Roomspot r"),
    @NamedQuery(name = "Roomspot.findById", query = "SELECT r FROM Roomspot r WHERE r.id = :id"),
    @NamedQuery(name = "Roomspot.findByRoomCode", query = "SELECT r FROM Roomspot r WHERE r.roomCode = :roomCode"),
    @NamedQuery(name = "Roomspot.findByRoomName", query = "SELECT r FROM Roomspot r WHERE r.roomName = :roomName"),
    @NamedQuery(name = "Roomspot.findBySecondName", query = "SELECT r FROM Roomspot r WHERE r.secondName = :secondName"),
//    @NamedQuery(name = "Roomspot.findByProvince", query = "SELECT r FROM Roomspot r WHERE r.address.province = :province"),
//    @NamedQuery(name = "Roomspot.findByCity", query = "SELECT r FROM Roomspot r WHERE r.address.city = :city"),
//    @NamedQuery(name = "Roomspot.findByCounty", query = "SELECT r FROM Roomspot r WHERE r.address.county = :county"),
    @NamedQuery(name = "Roomspot.findByGrid", query = "SELECT r FROM Roomspot r WHERE r.grid = :grid"),
    @NamedQuery(name = "Roomspot.findByPropertyOwner", query = "SELECT r FROM Roomspot r WHERE r.propertyOwner = :propertyOwner"),
    @NamedQuery(name = "Roomspot.findByStatus", query = "SELECT r FROM Roomspot r WHERE r.status = :status")})
public class Roomspot extends DatedEntity implements Serializable, Labeled {

    private static final long serialVersionUID = 1L;

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

    @Basic
    @Size(min = 1, max = 45)
    @Column(nullable = true, length = 45)
    private String secondName;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String grid;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String propertyOwner;

    @Size(max = 45)
    @Column(length = 45)
    private String status;

    @Lob
    @Size(max = 65535)
    @Column(length = 65535)
    private String commont;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "roomspot", orphanRemoval = false)
    private List<Station> stationList;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "roomspotId", orphanRemoval = false)
    private List<StationProperty> stationPropertyList;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "roomspot", orphanRemoval = false)
    private List<Netnode> netnodeList;

    @Embedded  
    private Address address;

    @Temporal(TemporalType.DATE)
    private Date productionStartTime;

    /**
     * Get the value of productionStartTime
     *
     * @return the value of productionStartTime
     */
    public Date getProductionStartTime() {
        return productionStartTime;
    }

    /**
     * Set the value of productionStartTime
     *
     * @param productionStartTime new value of productionStartTime
     */
    public void setProductionStartTime(Date productionStartTime) {
        this.productionStartTime = productionStartTime;
    }

    /**
     * Get the value of address
     *
     * @return the value of address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Set the value of address
     *
     * @param address new value of address
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    public Roomspot() {
    }

    public Roomspot(String id) {
        super(id);
    }

    public Roomspot(String id, String roomCode, String roomName, String grid, String propertyOwner) {
        this(id);
        this.roomCode = roomCode;
        this.roomName = roomName;

        this.grid = grid;
        this.propertyOwner = propertyOwner;
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

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String SecondName) {
        this.secondName = SecondName;
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
    public List<StationProperty> getStationPropertyList() {
        return stationPropertyList;
    }

    public void setStationPropertyList(List<StationProperty> stationPropertyList) {
        this.stationPropertyList = stationPropertyList;
    }

    @XmlTransient
    public List<Station> getStationList() {
        return stationList;
    }

    public void setStationList(List<Station> stationList) {
        this.stationList = stationList;
    }

   

    @Override
    public String getLabel() {
        return roomCode + "|" + roomName;
    }

    public List<Netnode> getNetnodeList() {
        return netnodeList;
    }

    public void setNetnodeList(List<Netnode> netnodeList) {
        this.netnodeList = netnodeList;
    }

}
