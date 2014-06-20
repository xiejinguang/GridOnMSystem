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
@Table(name = "sec_user_role")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SecUserRole.findAll", query = "SELECT s FROM SecUserRole s"),
    @NamedQuery(name = "SecUserRole.findByIduserRole", query = "SELECT s FROM SecUserRole s WHERE s.iduserRole = :iduserRole")})
public class SecUserRole implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "iduser_role", nullable = false, length = 36)
    private String iduserRole;
    @JoinColumn(name = "roleId", referencedColumnName = "roleId", nullable = false)
    @ManyToOne(optional = false)
    private SecRole roleId;
    @JoinColumn(name = "username", referencedColumnName = "username", nullable = false)
    @ManyToOne(optional = false)
    private SecUser username;

    public SecUserRole() {
    }

    public SecUserRole(String iduserRole) {
        this.iduserRole = iduserRole;
    }

    public String getIduserRole() {
        return iduserRole;
    }

    public void setIduserRole(String iduserRole) {
        this.iduserRole = iduserRole;
    }

    public SecRole getRoleId() {
        return roleId;
    }

    public void setRoleId(SecRole roleId) {
        this.roleId = roleId;
    }

    public SecUser getUsername() {
        return username;
    }

    public void setUsername(SecUser username) {
        this.username = username;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iduserRole != null ? iduserRole.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SecUserRole)) {
            return false;
        }
        SecUserRole other = (SecUserRole) object;
        if ((this.iduserRole == null && other.iduserRole != null) || (this.iduserRole != null && !this.iduserRole.equals(other.iduserRole))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.peasant.security.model.SecUserRole[ iduserRole=" + iduserRole + " ]";
    }
    
}
