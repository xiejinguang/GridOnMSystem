/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eman.basic.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.peasant.jpa.DatedEntity;
import org.peasant.jpa.UUIDEntity;

/**
 *
 * @author 谢金光
 */
@Entity
@Table(catalog = "eman_basic", name = "equipment_model")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE", discriminatorType = DiscriminatorType.STRING, length = 25)
@NamedQueries({
    @NamedQuery(name = "EquipmentModel.findAll", query = "SELECT e FROM EquipmentModel e"),
    @NamedQuery(name = "EquipmentModel.findById", query = "SELECT e FROM EquipmentModel e WHERE e.id = :id"),
    @NamedQuery(name = "EquipmentModel.findByType", query = "SELECT e FROM EquipmentModel e WHERE e.type = :type"),
    @NamedQuery(name = "EquipmentModel.findByClass1", query = "SELECT e FROM EquipmentModel e WHERE e.category = :category"),
    @NamedQuery(name = "EquipmentModel.findByModel", query = "SELECT e FROM EquipmentModel e WHERE e.model = :model"),
    @NamedQuery(name = "EquipmentModel.findByManufacturer", query = "SELECT e FROM EquipmentModel e WHERE e.manufacturer = :manufacturer")})

public class EquipmentModel extends DatedEntity implements Serializable {

    protected static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @NotNull
    @Size(max = 25)
    @Column(nullable = false, name = "TYPE", length = 25)
    protected String type;//大类别，网络主设备，动力配套

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String category;//次类别，

    @Basic(optional = false)
    @NotNull
    @Size(max = 45)
    @Column(nullable = false, length = 45)
    protected String model; //型号
    @Size(max = 45)
    @Column(length = 45)
    protected String manufacturer; //制造商
    @Lob
    @Size(max = 65535)
    @Column(length = 65535)
    protected String commont;
    @Lob
    @Size(max = 65535)
    @Column(length = 65535)
    private String functionBrief;

    public EquipmentModel() {
    }

    public EquipmentModel(String id) {
        super(id);
    }

    /**
     * Get the value of function
     *
     * @return the value of function
     */
    public String getFunctionBrief() {
        return functionBrief;
    }

    /**
     * Set the value of function
     *
     * @param functionBrief new value of function
     */
    public void setFunctionBrief(String functionBrief) {
        this.functionBrief = functionBrief;
    }

    /**
     * Get the value of category
     *
     * @return the value of category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Set the value of category
     *
     * @param category new value of category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EquipmentModel)) {
            return false;
        }
        EquipmentModel other = (EquipmentModel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.eman.basic.model.EquipmentModel[ id=" + id + " ]";
    }

}
