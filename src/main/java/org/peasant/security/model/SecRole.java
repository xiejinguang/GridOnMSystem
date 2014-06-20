/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.peasant.security.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
@Table(name = "sec_role", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"roleName"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SecRole.findAll", query = "SELECT s FROM SecRole s"),
    @NamedQuery(name = "SecRole.findByRoleId", query = "SELECT s FROM SecRole s WHERE s.roleId = :roleId"),
    @NamedQuery(name = "SecRole.findByRoleName", query = "SELECT s FROM SecRole s WHERE s.roleName = :roleName"),
    @NamedQuery(name = "SecRole.findByDescription", query = "SELECT s FROM SecRole s WHERE s.description = :description"),
    @NamedQuery(name = "SecRole.findByCreateTime", query = "SELECT s FROM SecRole s WHERE s.createTime = :createTime")})
public class SecRole implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(nullable = false, length = 36)
    private String roleId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String roleName;
    @Size(max = 45)
    @Column(length = 45)
    private String description;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "roleId")
    private Collection<SecUserRole> secUserRoleCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "roleroleId")
    private Collection<SecRolePermission> secRolePermissionCollection;

    public SecRole() {
    }

    public SecRole(String roleId) {
        this.roleId = roleId;
    }

    public SecRole(String roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @XmlTransient
    public Collection<SecUserRole> getSecUserRoleCollection() {
        return secUserRoleCollection;
    }

    public void setSecUserRoleCollection(Collection<SecUserRole> secUserRoleCollection) {
        this.secUserRoleCollection = secUserRoleCollection;
    }

    @XmlTransient
    public Collection<SecRolePermission> getSecRolePermissionCollection() {
        return secRolePermissionCollection;
    }

    public void setSecRolePermissionCollection(Collection<SecRolePermission> secRolePermissionCollection) {
        this.secRolePermissionCollection = secRolePermissionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roleId != null ? roleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SecRole)) {
            return false;
        }
        SecRole other = (SecRole) object;
        if ((this.roleId == null && other.roleId != null) || (this.roleId != null && !this.roleId.equals(other.roleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.peasant.security.model.SecRole[ roleId=" + roleId + " ]";
    }
    
}
