/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.ORGStructure.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
@Table(name = "department", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"departCode"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Department_1.findAll", query = "SELECT d FROM Department_1 d"),
    @NamedQuery(name = "Department_1.findById", query = "SELECT d FROM Department_1 d WHERE d.id = :id"),
    @NamedQuery(name = "Department_1.findByDepartCode", query = "SELECT d FROM Department_1 d WHERE d.departCode = :departCode"),
    @NamedQuery(name = "Department_1.findByName", query = "SELECT d FROM Department_1 d WHERE d.name = :name")})
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(nullable = false, length = 36)
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(nullable = false, length = 10)
    private String departCode;
    @Basic(optional = false)
    @Size(max = 45)
    @Column(nullable = false, length = 45)
    private String name;
    @Lob
    @Size(max = 65535)
    @Column(length = 65535)
    private String description;
    @OneToMany(mappedBy = "superior")
    private Collection<Department> departmentCollection;
    @JoinColumn(name = "superior", referencedColumnName = "departCode")
    @ManyToOne
    private Department superior;

    public Department() {
    }

    public Department(String id) {
        this.id = id;
    }

    public Department(String id, String departCode) {
        this.id = id;
        this.departCode = departCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepartCode() {
        return departCode;
    }

    public void setDepartCode(String departCode) {
        this.departCode = departCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public Collection<Department> getDepartmentCollection() {
        return departmentCollection;
    }

    public void setDepartmentCollection(Collection<Department> departmentCollection) {
        this.departmentCollection = departmentCollection;
    }

    public Department getSuperior() {
        return superior;
    }

    public void setSuperior(Department superior) {
        this.superior = superior;
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
        if (!(object instanceof Department)) {
            return false;
        }
        Department other = (Department) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.peasant.ORGStructure.model.Department_1[ id=" + id + " ]";
    }

}
