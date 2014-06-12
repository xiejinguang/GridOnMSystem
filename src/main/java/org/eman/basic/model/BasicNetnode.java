/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.eman.basic.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 谢金光
 */
@Entity
@Table(name = "basic_netnode", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"ossCode"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BasicNetnode.findAll", query = "SELECT b FROM BasicNetnode b"),
    @NamedQuery(name = "BasicNetnode.findByNetNodeID", query = "SELECT b FROM BasicNetnode b WHERE b.netNodeID = :netNodeID"),
    @NamedQuery(name = "BasicNetnode.findByOssCode", query = "SELECT b FROM BasicNetnode b WHERE b.ossCode = :ossCode"),
    @NamedQuery(name = "BasicNetnode.findByName", query = "SELECT b FROM BasicNetnode b WHERE b.name = :name"),
    @NamedQuery(name = "BasicNetnode.findByInvestTime", query = "SELECT b FROM BasicNetnode b WHERE b.investTime = :investTime"),
    @NamedQuery(name = "BasicNetnode.findByStatus", query = "SELECT b FROM BasicNetnode b WHERE b.status = :status")})
public class BasicNetnode implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(nullable = false, length = 36)
    private String netNodeID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String ossCode;
    @Size(max = 45)
    @Column(length = 45)
    private String name;
    @Temporal(TemporalType.DATE)
    private Date investTime;
    @Lob
    @Size(max = 65535)
    @Column(length = 65535)
    private String commont;
    @Size(max = 45)
    @Column(length = 45)
    private String status;
    @JoinColumn(name = "equipModelID", referencedColumnName = "equipModelID", nullable = false)
    @ManyToOne(optional = false)
    private BasicEquipmentModel equipModelID;
    @JoinColumn(name = "statID", referencedColumnName = "statID", nullable = false)
    @ManyToOne(optional = false)
    private BasicStation statID;

    public BasicNetnode() {
    }

    public BasicNetnode(String netNodeID) {
        this.netNodeID = netNodeID;
    }

    public BasicNetnode(String netNodeID, String ossCode) {
        this.netNodeID = netNodeID;
        this.ossCode = ossCode;
    }

    public String getNetNodeID() {
        return netNodeID;
    }

    public void setNetNodeID(String netNodeID) {
        this.netNodeID = netNodeID;
    }

    public String getOssCode() {
        return ossCode;
    }

    public void setOssCode(String ossCode) {
        this.ossCode = ossCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getInvestTime() {
        return investTime;
    }

    public void setInvestTime(Date investTime) {
        this.investTime = investTime;
    }

    public String getCommont() {
        return commont;
    }

    public void setCommont(String commont) {
        this.commont = commont;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BasicEquipmentModel getEquipModelID() {
        return equipModelID;
    }

    public void setEquipModelID(BasicEquipmentModel equipModelID) {
        this.equipModelID = equipModelID;
    }

    public BasicStation getStatID() {
        return statID;
    }

    public void setStatID(BasicStation statID) {
        this.statID = statID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (netNodeID != null ? netNodeID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BasicNetnode)) {
            return false;
        }
        BasicNetnode other = (BasicNetnode) object;
        if ((this.netNodeID == null && other.netNodeID != null) || (this.netNodeID != null && !this.netNodeID.equals(other.netNodeID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.eman.basic.model.BasicNetnode[ netNodeID=" + netNodeID + " ]";
    }
    
}
