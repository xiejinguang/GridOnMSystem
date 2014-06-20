/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.peasant.security.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
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
@Table(name = "sec_permission")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SecPermission.findAll", query = "SELECT s FROM SecPermission s"),
    @NamedQuery(name = "SecPermission.findByPermissionId", query = "SELECT s FROM SecPermission s WHERE s.permissionId = :permissionId"),
    @NamedQuery(name = "SecPermission.findByPermName", query = "SELECT s FROM SecPermission s WHERE s.permName = :permName"),
    @NamedQuery(name = "SecPermission.findByCode", query = "SELECT s FROM SecPermission s WHERE s.code = :code"),
    @NamedQuery(name = "SecPermission.findByDomain", query = "SELECT s FROM SecPermission s WHERE s.domain = :domain"),
    @NamedQuery(name = "SecPermission.findByAction", query = "SELECT s FROM SecPermission s WHERE s.action = :action"),
    @NamedQuery(name = "SecPermission.findByTarget", query = "SELECT s FROM SecPermission s WHERE s.target = :target"),
    @NamedQuery(name = "SecPermission.findByDescription", query = "SELECT s FROM SecPermission s WHERE s.description = :description")})
public class SecPermission implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(nullable = false, length = 36)
    private String permissionId;
    @Size(max = 45)
    @Column(length = 45)
    private String permName;
    @Size(max = 45)
    @Column(length = 45)
    private String code;
    @Size(max = 45)
    @Column(length = 45)
    private String domain;
    @Size(max = 45)
    @Column(length = 45)
    private String action;
    @Size(max = 45)
    @Column(length = 45)
    private String target;
    @Size(max = 45)
    @Column(length = 45)
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "permissionpermissionId")
    private Collection<SecRolePermission> secRolePermissionCollection;

    public SecPermission() {
    }

    public SecPermission(String permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermName() {
        return permName;
    }

    public void setPermName(String permName) {
        this.permName = permName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        hash += (permissionId != null ? permissionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SecPermission)) {
            return false;
        }
        SecPermission other = (SecPermission) object;
        if ((this.permissionId == null && other.permissionId != null) || (this.permissionId != null && !this.permissionId.equals(other.permissionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.peasant.security.model.SecPermission[ permissionId=" + permissionId + " ]";
    }
    
}
