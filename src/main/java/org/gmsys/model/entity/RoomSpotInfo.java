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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "room_spot_info")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RoomSpotInfo.findAll", query = "SELECT r FROM RoomSpotInfo r"),
    @NamedQuery(name = "RoomSpotInfo.findById", query = "SELECT r FROM RoomSpotInfo r WHERE r.id = :id"),
    @NamedQuery(name = "RoomSpotInfo.findByCode", query = "SELECT r FROM RoomSpotInfo r WHERE r.code = :code"),
    @NamedQuery(name = "RoomSpotInfo.findByName", query = "SELECT r FROM RoomSpotInfo r WHERE r.name = :name"),
    @NamedQuery(name = "RoomSpotInfo.findByProvince", query = "SELECT r FROM RoomSpotInfo r WHERE r.province = :province"),
    @NamedQuery(name = "RoomSpotInfo.findByCity", query = "SELECT r FROM RoomSpotInfo r WHERE r.city = :city"),
    @NamedQuery(name = "RoomSpotInfo.findByCounty", query = "SELECT r FROM RoomSpotInfo r WHERE r.county = :county")})
public class RoomSpotInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "code")
    private String code;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "province")
    private String province;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "city")
    private String city;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "county")
    private String county;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "roomSpotInfoId")
    private Collection<StationInfo> stationInfoCollection;
    @JoinColumn(name = "grid_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Grid gridId;

    public RoomSpotInfo() {
    }

    public RoomSpotInfo(String id) {
        this.id = id;
    }

    public RoomSpotInfo(String id, String code, String name, String province, String city, String county) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.province = province;
        this.city = city;
        this.county = county;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @XmlTransient
    public Collection<StationInfo> getStationInfoCollection() {
        return stationInfoCollection;
    }

    public void setStationInfoCollection(Collection<StationInfo> stationInfoCollection) {
        this.stationInfoCollection = stationInfoCollection;
    }

    public Grid getGridId() {
        return gridId;
    }

    public void setGridId(Grid gridId) {
        this.gridId = gridId;
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
        if (!(object instanceof RoomSpotInfo)) {
            return false;
        }
        RoomSpotInfo other = (RoomSpotInfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.gmsys.model.entity.RoomSpotInfo[ id=" + id + " ]";
    }
    
}
