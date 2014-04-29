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
@Table(name = "grid", catalog = "jobpromotion", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"name"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Grid.findAll", query = "SELECT g FROM Grid g"),
    @NamedQuery(name = "Grid.findById", query = "SELECT g FROM Grid g WHERE g.id = :id"),
    @NamedQuery(name = "Grid.findByName", query = "SELECT g FROM Grid g WHERE g.name = :name"),
    @NamedQuery(name = "Grid.findByManager", query = "SELECT g FROM Grid g WHERE g.manager = :manager")})
public class Grid implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "name", nullable = false, length = 45)
    private String name;
    @Size(max = 45)
    @Column(name = "manager", length = 45)
    private String manager;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gridId")
    private Collection<RoomSpotInfo> roomSpotInfoCollection;

    public Grid() {
    }

    public Grid(Integer id) {
        this.id = id;
    }

    public Grid(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Grid)) {
            return false;
        }
        Grid other = (Grid) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.gmsys.model.entity.Grid[ id=" + id + " ]";
    }
    
}
