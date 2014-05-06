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
@Table(name = "station_detail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StationDetail.findAll", query = "SELECT s FROM StationDetail s"),
    @NamedQuery(name = "StationDetail.findByStatId", query = "SELECT s FROM StationDetail s WHERE s.statId = :statId"),
    @NamedQuery(name = "StationDetail.findByStatCode", query = "SELECT s FROM StationDetail s WHERE s.statCode = :statCode"),
    @NamedQuery(name = "StationDetail.findByStatName", query = "SELECT s FROM StationDetail s WHERE s.statName = :statName"),
    @NamedQuery(name = "StationDetail.findByStatAddress", query = "SELECT s FROM StationDetail s WHERE s.statAddress = :statAddress"),
    @NamedQuery(name = "StationDetail.findByStatOwner", query = "SELECT s FROM StationDetail s WHERE s.statOwner = :statOwner"),
    @NamedQuery(name = "StationDetail.findByStatType", query = "SELECT s FROM StationDetail s WHERE s.statType = :statType"),
    @NamedQuery(name = "StationDetail.findByStatAccomMaintainer", query = "SELECT s FROM StationDetail s WHERE s.statAccomMaintainer = :statAccomMaintainer"),
    @NamedQuery(name = "StationDetail.findByRoomId", query = "SELECT s FROM StationDetail s WHERE s.roomId = :roomId"),
    @NamedQuery(name = "StationDetail.findByRoomCode", query = "SELECT s FROM StationDetail s WHERE s.roomCode = :roomCode"),
    @NamedQuery(name = "StationDetail.findByRoomName", query = "SELECT s FROM StationDetail s WHERE s.roomName = :roomName"),
    @NamedQuery(name = "StationDetail.findByGridId", query = "SELECT s FROM StationDetail s WHERE s.gridId = :gridId"),
    @NamedQuery(name = "StationDetail.findByGridName", query = "SELECT s FROM StationDetail s WHERE s.gridName = :gridName"),
    @NamedQuery(name = "StationDetail.findByManager", query = "SELECT s FROM StationDetail s WHERE s.manager = :manager")})
public class StationDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Id
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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    private String roomId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    private String roomCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    private String roomName;
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

    public StationDetail() {
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
    
}
