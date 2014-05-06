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
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "grid_info")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GridInfo.findAll", query = "SELECT g FROM GridInfo g"),
    @NamedQuery(name = "GridInfo.findByGridId", query = "SELECT g FROM GridInfo g WHERE g.gridId = :gridId"),
    @NamedQuery(name = "GridInfo.findByGridName", query = "SELECT g FROM GridInfo g WHERE g.gridName = :gridName"),
    @NamedQuery(name = "GridInfo.findByManager", query = "SELECT g FROM GridInfo g WHERE g.manager = :manager")})
public class GridInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    private String gridId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    private String gridName;
    @Size(max = 45)
    private String manager;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gridId")
    private Collection<RoomSpotInfo> roomSpotInfoCollection;

    public GridInfo() {
    }

    public GridInfo(String gridId) {
        this.gridId = gridId;
    }

    public GridInfo(String gridId, String gridName) {
        this.gridId = gridId;
        this.gridName = gridName;
    }

    public String getGridId() {
        return gridId;
    }

    public void setGridId(String gridId) {
        this.gridId = gridId;
    }

    public String getGridName() {
        return gridName;
    }

    public void setGridName(String gridName) {
        this.gridName = gridName;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    @XmlTransient
    public Collection<RoomSpotInfo> getRoomSpotInfoCollection() {
        return roomSpotInfoCollection;
    }

    public void setRoomSpotInfoCollection(Collection<RoomSpotInfo> roomSpotInfoCollection) {
        this.roomSpotInfoCollection = roomSpotInfoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gridId != null ? gridId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GridInfo)) {
            return false;
        }
        GridInfo other = (GridInfo) object;
        if ((this.gridId == null && other.gridId != null) || (this.gridId != null && !this.gridId.equals(other.gridId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.gmsys.view.util.GridInfo[ gridId=" + gridId + " ]";
    }
    
}
