/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gmsys.model.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 谢金光
 */
@Entity
@Table(name = "station_info")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StationInfo.findAll", query = "SELECT s FROM StationInfo s"),
    @NamedQuery(name = "StationInfo.findByStatId", query = "SELECT s FROM StationInfo s WHERE s.statId = :statId"),
    @NamedQuery(name = "StationInfo.findByStatCode", query = "SELECT s FROM StationInfo s WHERE s.statCode = :statCode"),
    @NamedQuery(name = "StationInfo.findByStatName", query = "SELECT s FROM StationInfo s WHERE s.statName = :statName"),
    @NamedQuery(name = "StationInfo.findByStatAddress", query = "SELECT s FROM StationInfo s WHERE s.statAddress = :statAddress"),
    @NamedQuery(name = "StationInfo.findByStatOwner", query = "SELECT s FROM StationInfo s WHERE s.statOwner = :statOwner"),
    @NamedQuery(name = "StationInfo.findByStatType", query = "SELECT s FROM StationInfo s WHERE s.statType = :statType"),
    @NamedQuery(name = "StationInfo.findByStatAccomMaintainer", query = "SELECT s FROM StationInfo s WHERE s.statAccomMaintainer = :statAccomMaintainer")})
public class StationInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    private String statId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    private String statCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    private String statName;
    @Size(max = 45)
    private String statAddress;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    private String statOwner;
    @Size(max = 45)
    private String statType;
    @Size(max = 45)
    private String statAccomMaintainer;
    @JoinColumn(name = "roomId", referencedColumnName = "roomId")
    @ManyToOne(optional = false)
    private RoomSpotInfo roomId;

    public StationInfo() {
    }

    public StationInfo(String statId) {
        this.statId = statId;
    }

    public StationInfo(String statId, String statCode, String statName, String statOwner) {
        this.statId = statId;
        this.statCode = statCode;
        this.statName = statName;
        this.statOwner = statOwner;
    }

    public String getStatId() {
        return statId;
    }

    public void setStatId(String statId) {
        this.statId = statId;
    }

    public String getStatCode() {
        return statCode;
    }

    public void setStatCode(String statCode) {
        this.statCode = statCode;
    }

    public String getStatName() {
        return statName;
    }

    public void setStatName(String statName) {
        this.statName = statName;
    }

    public String getStatAddress() {
        return statAddress;
    }

    public void setStatAddress(String statAddress) {
        this.statAddress = statAddress;
    }

    public String getStatOwner() {
        return statOwner;
    }

    public void setStatOwner(String statOwner) {
        this.statOwner = statOwner;
    }

    public String getStatType() {
        return statType;
    }

    public void setStatType(String statType) {
        this.statType = statType;
    }

    public String getStatAccomMaintainer() {
        return statAccomMaintainer;
    }

    public void setStatAccomMaintainer(String statAccomMaintainer) {
        this.statAccomMaintainer = statAccomMaintainer;
    }

    public RoomSpotInfo getRoomId() {
        return roomId;
    }

    public void setRoomId(RoomSpotInfo roomId) {
        this.roomId = roomId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (statId != null ? statId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StationInfo)) {
            return false;
        }
        StationInfo other = (StationInfo) object;
        if ((this.statId == null && other.statId != null) || (this.statId != null && !this.statId.equals(other.statId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.gmsys.view.util.StationInfo[ statId=" + statId + " ]";
    }
    
}
