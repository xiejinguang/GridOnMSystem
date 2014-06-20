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
@Table(name = "sec_resource")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SecResource.findAll", query = "SELECT s FROM SecResource s"),
    @NamedQuery(name = "SecResource.findByResourceId", query = "SELECT s FROM SecResource s WHERE s.resourceId = :resourceId"),
    @NamedQuery(name = "SecResource.findByResCode", query = "SELECT s FROM SecResource s WHERE s.resCode = :resCode"),
    @NamedQuery(name = "SecResource.findByResLabel", query = "SELECT s FROM SecResource s WHERE s.resLabel = :resLabel"),
    @NamedQuery(name = "SecResource.findByPermCode", query = "SELECT s FROM SecResource s WHERE s.permCode = :permCode")})
public class SecResource implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(nullable = false, length = 36)
    private String resourceId;
    @Size(max = 45)
    @Column(length = 45)
    private String resCode;
    @Size(max = 45)
    @Column(length = 45)
    private String resLabel;
    @Size(max = 45)
    @Column(length = 45)
    private String permCode;

    public SecResource() {
    }

    public SecResource(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResLabel() {
        return resLabel;
    }

    public void setResLabel(String resLabel) {
        this.resLabel = resLabel;
    }

    public String getPermCode() {
        return permCode;
    }

    public void setPermCode(String permCode) {
        this.permCode = permCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (resourceId != null ? resourceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SecResource)) {
            return false;
        }
        SecResource other = (SecResource) object;
        if ((this.resourceId == null && other.resourceId != null) || (this.resourceId != null && !this.resourceId.equals(other.resourceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.peasant.security.model.SecResource[ resourceId=" + resourceId + " ]";
    }
    
}
