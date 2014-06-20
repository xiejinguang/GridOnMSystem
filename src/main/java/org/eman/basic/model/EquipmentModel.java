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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author 谢金光
 */
@Entity
@Table(name = "basic_equipment_model")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EquipmentModel.findAll", query = "SELECT e FROM EquipmentModel e"),
    @NamedQuery(name = "EquipmentModel.findByEquipModelID", query = "SELECT e FROM EquipmentModel e WHERE e.equipModelID = :equipModelID"),
    @NamedQuery(name = "EquipmentModel.findByType", query = "SELECT e FROM EquipmentModel e WHERE e.type = :type"),
    @NamedQuery(name = "EquipmentModel.findByClass1", query = "SELECT e FROM EquipmentModel e WHERE e.class1 = :class1"),
    @NamedQuery(name = "EquipmentModel.findByNetType", query = "SELECT e FROM EquipmentModel e WHERE e.netType = :netType"),
    @NamedQuery(name = "EquipmentModel.findByModel", query = "SELECT e FROM EquipmentModel e WHERE e.model = :model"),
    @NamedQuery(name = "EquipmentModel.findByManufacturer", query = "SELECT e FROM EquipmentModel e WHERE e.manufacturer = :manufacturer")})
public class EquipmentModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(nullable = false, length = 36)
    private String equipModelID;
    @Size(max = 45)
    @Column(length = 45)
    private String type;
    @Size(max = 45)
    @Column(name = "class", length = 45)
    private String class1;
    @Size(max = 45)
    @Column(length = 45)
    private String netType;
    @Size(max = 45)
    @Column(length = 45)
    private String model;
    @Size(max = 45)
    @Column(length = 45)
    private String manufacturer;
    @Lob
    @Size(max = 65535)
    @Column(length = 65535)
    private String commont;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "equipModelID")
    private Collection<Netnode> netnodeCollection;

    public EquipmentModel() {
    }

    public EquipmentModel(String equipModelID) {
        this.equipModelID = equipModelID;
    }

    public String getEquipModelID() {
        return equipModelID;
    }

    public void setEquipModelID(String equipModelID) {
        this.equipModelID = equipModelID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClass1() {
        return class1;
    }

    public void setClass1(String class1) {
        this.class1 = class1;
    }

    public String getNetType() {
        return netType;
    }

    public void setNetType(String netType) {
        this.netType = netType;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getCommont() {
        return commont;
    }

    public void setCommont(String commont) {
        this.commont = commont;
    }

    @XmlTransient
    public Collection<Netnode> getNetnodeCollection() {
        return netnodeCollection;
    }

    public void setNetnodeCollection(Collection<Netnode> netnodeCollection) {
        this.netnodeCollection = netnodeCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (equipModelID != null ? equipModelID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EquipmentModel)) {
            return false;
        }
        EquipmentModel other = (EquipmentModel) object;
        if ((this.equipModelID == null && other.equipModelID != null) || (this.equipModelID != null && !this.equipModelID.equals(other.equipModelID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.eman.assit.model.EquipmentModel[ equipModelID=" + equipModelID + " ]";
    }
    
}
