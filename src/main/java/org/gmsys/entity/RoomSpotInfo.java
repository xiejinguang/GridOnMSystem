/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gmsys.entity;

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
    @NamedQuery(name = "RoomSpotInfo.findByRoomId", query = "SELECT r FROM RoomSpotInfo r WHERE r.roomId = :roomId"),
    @NamedQuery(name = "RoomSpotInfo.findByRoomCode", query = "SELECT r FROM RoomSpotInfo r WHERE r.roomCode = :roomCode"),
    @NamedQuery(name = "RoomSpotInfo.findByRoomName", query = "SELECT r FROM RoomSpotInfo r WHERE r.roomName = :roomName"),
    @NamedQuery(name = "RoomSpotInfo.findByProvince", query = "SELECT r FROM RoomSpotInfo r WHERE r.province = :province"),
    @NamedQuery(name = "RoomSpotInfo.findByCity", query = "SELECT r FROM RoomSpotInfo r WHERE r.city = :city"),
    @NamedQuery(name = "RoomSpotInfo.findByCounty", query = "SELECT r FROM RoomSpotInfo r WHERE r.county = :county")})
public class RoomSpotInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String roomId;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "roomId")
    private Collection<StationInfo> stationInfoCollection;
    @JoinColumn(name = "gridId", referencedColumnName = "gridId", nullable = false)
    @ManyToOne(optional = false)
    private GridInfo gridId;

    public RoomSpotInfo() {
    }

    public RoomSpotInfo(String roomId) {
        this.roomId = roomId;
    }

    public RoomSpotInfo(String roomId, String roomCode, String roomName, String province, String city, String county) {
        this.roomId = roomId;
        this.roomCode = roomCode;
        this.roomName = roomName;
        this.province = province;
        this.city = city;
        this.county = county;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
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

    @XmlTransient
    public Collection<StationInfo> getStationInfoCollection() {
        return stationInfoCollection;
    }

    public void setStationInfoCollection(Collection<StationInfo> stationInfoCollection) {
        this.stationInfoCollection = stationInfoCollection;
    }

    public GridInfo getGridId() {
        return gridId;
    }

    public void setGridId(GridInfo gridId) {
        this.gridId = gridId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roomId != null ? roomId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RoomSpotInfo)) {
            return false;
        }
        RoomSpotInfo other = (RoomSpotInfo) object;
        if ((this.roomId == null && other.roomId != null) || (this.roomId != null && !this.roomId.equals(other.roomId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.gmsys.entity.RoomSpotInfo[ roomId=" + roomId + " ]";
    }
    
}
