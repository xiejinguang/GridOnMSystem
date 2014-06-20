/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.peasant.security.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
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
@Table(name = "sec_role_permission")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RolePermission.findAll", query = "SELECT r FROM RolePermission r"),
    @NamedQuery(name = "RolePermission.findByIdrolePerm", query = "SELECT r FROM RolePermission r WHERE r.idrolePerm = :idrolePerm")})
public class RolePermission implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "idrole_perm", nullable = false, length = 36)
    private String idrolePerm;
    @JoinColumn(name = "permission_permissionId", referencedColumnName = "permissionId", nullable = false)
    @ManyToOne(optional = false)
    private Permission permissionpermissionId;
    @JoinColumn(name = "role_roleId", referencedColumnName = "roleId", nullable = false)
    @ManyToOne(optional = false)
    private Role roleroleId;

    public RolePermission() {
    }

    public RolePermission(String idrolePerm) {
        this.idrolePerm = idrolePerm;
    }

    public String getIdrolePerm() {
        return idrolePerm;
    }

    public void setIdrolePerm(String idrolePerm) {
        this.idrolePerm = idrolePerm;
    }

    public Permission getPermissionpermissionId() {
        return permissionpermissionId;
    }

    public void setPermissionpermissionId(Permission permissionpermissionId) {
        this.permissionpermissionId = permissionpermissionId;
    }

    public Role getRoleroleId() {
        return roleroleId;
    }

    public void setRoleroleId(Role roleroleId) {
        this.roleroleId = roleroleId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idrolePerm != null ? idrolePerm.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RolePermission)) {
            return false;
        }
        RolePermission other = (RolePermission) object;
        if ((this.idrolePerm == null && other.idrolePerm != null) || (this.idrolePerm != null && !this.idrolePerm.equals(other.idrolePerm))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.peasant.security.model.RolePermission[ idrolePerm=" + idrolePerm + " ]";
    }
    
}
