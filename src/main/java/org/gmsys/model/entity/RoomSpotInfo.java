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
@Table(name = "room_spot_info", catalog = "jobpromotion", schema = "")
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
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "code", nullable = false, length = 45)
    private String code;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "name", nullable = false, length = 45)
    private String name;
    @Size(max = 45)
    @Column(name = "province", length = 45)
    private String province;
    @Size(max = 45)
    @Column(name = "city", length = 45)
    private String city;
    @Size(max = 45)
    @Column(name = "county", length = 45)
    private String county;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "roomSpotInfoId")
    private Collection<StationInfo> stationInfoCollection;
    @JoinColumn(name = "grid_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Grid gridId;

    public RoomSpotInfo() {
    }

    public RoomSpotInfo(Integer id) {
        this.id = id;
    }

    public RoomSpotInfo(Integer id, String code, String name) {
        this.id = id;
        this.code = code;
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
