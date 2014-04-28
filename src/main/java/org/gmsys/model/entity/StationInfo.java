/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gmsys.model.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author 谢金光
 */
@Entity
@Table(name = "station_info")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StationInfo.findAll", query = "SELECT s FROM StationInfo s"),
    @NamedQuery(name = "StationInfo.findById", query = "SELECT s FROM StationInfo s WHERE s.id = :id"),
    @NamedQuery(name = "StationInfo.findByCode", query = "SELECT s FROM StationInfo s WHERE s.code = :code"),
    @NamedQuery(name = "StationInfo.findByRoomCode", query = "SELECT s FROM StationInfo s WHERE s.roomCode = :roomCode"),
    @NamedQuery(name = "StationInfo.findByName", query = "SELECT s FROM StationInfo s WHERE s.name = :name"),
    @NamedQuery(name = "StationInfo.findByCity", query = "SELECT s FROM StationInfo s WHERE s.city = :city"),
    @NamedQuery(name = "StationInfo.findByProvince", query = "SELECT s FROM StationInfo s WHERE s.province = :province"),
    @NamedQuery(name = "StationInfo.findByCounty", query = "SELECT s FROM StationInfo s WHERE s.county = :county"),
    @NamedQuery(name = "StationInfo.findByAddress", query = "SELECT s FROM StationInfo s WHERE s.address = :address"),
    @NamedQuery(name = "StationInfo.findByOwner", query = "SELECT s FROM StationInfo s WHERE s.owner = :owner"),
    @NamedQuery(name = "StationInfo.findByType", query = "SELECT s FROM StationInfo s WHERE s.type = :type"),
    @NamedQuery(name = "StationInfo.findByAccomMaintainer", query = "SELECT s FROM StationInfo s WHERE s.accomMaintainer = :accomMaintainer")})
public class StationInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "code")
    private String code;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "room_code")
    private String roomCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "name")
    private String name;
    @Size(max = 45)
    @Column(name = "city")
    private String city;
    @Size(max = 45)
    @Column(name = "province")
    private String province;
    @Size(max = 45)
    @Column(name = "county")
    private String county;
    @Size(max = 45)
    @Column(name = "address")
    private String address;
    @Size(max = 45)
    @Column(name = "owner")
    private String owner;
    @Size(max = 45)
    @Column(name = "type")
    private String type;
    @Size(max = 45)
    @Column(name = "accom_maintainer")
    private String accomMaintainer;
    @Lob
    @Column(name = "blobcol")
    private byte[] blobcol;
    @JoinColumn(name = "grid", referencedColumnName = "name")
    @ManyToOne(optional = false)
    private Grid grid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stationName")
    private Collection<FixNeeds> fixNeedsCollection;

    public StationInfo() {
    }

    public StationInfo(Integer id) {
        this.id = id;
    }

    public StationInfo(Integer id, String roomCode, String name) {
        this.id = id;
        this.roomCode = roomCode;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
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

    public byte[] getBlobcol() {
        return blobcol;
    }

    public void setBlobcol(byte[] blobcol) {
        this.blobcol = blobcol;
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    @XmlTransient
    public Collection<FixNeeds> getFixNeedsCollection() {
        return fixNeedsCollection;
    }

    public void setFixNeedsCollection(Collection<FixNeeds> fixNeedsCollection) {
        this.fixNeedsCollection = fixNeedsCollection;
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
        if (!(object instanceof StationInfo)) {
            return false;
        }
        StationInfo other = (StationInfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.gmsys.model.entity.StationInfo[ id=" + id + " ]";
    }
    
}
