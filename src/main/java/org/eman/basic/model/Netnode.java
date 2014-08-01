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
import org.peasant.model.Labeled;

/**
 *
 * @author 谢金光
 */
@Entity
@Table(name = "basic_netnode", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"ossCode"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Netnode.findAll", query = "SELECT n FROM Netnode n"),
    @NamedQuery(name = "Netnode.findById", query = "SELECT n FROM Netnode n WHERE n.id = :id"),
    @NamedQuery(name = "Netnode.findByOssCode", query = "SELECT n FROM Netnode n WHERE n.ossCode = :ossCode"),
    @NamedQuery(name = "Netnode.findByName", query = "SELECT n FROM Netnode n WHERE n.name = :name"),
    @NamedQuery(name = "Netnode.findByInvestTime", query = "SELECT n FROM Netnode n WHERE n.investTime = :investTime"),
    @NamedQuery(name = "Netnode.findByStatus", query = "SELECT n FROM Netnode n WHERE n.status = :status")})
public class Netnode implements Serializable,Labeled {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(nullable = false, length = 36)
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String ossCode;
    @Basic(optional = false)
    @Size(max = 45)
    @Column(nullable = false, length = 45)
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
    @JoinColumn(name = "equipModelId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private EquipmentModel equipModelId;
    @JoinColumn(name = "statID", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Station statID;

    public Netnode() {
    }

    public Netnode(String id) {
        this.id = id;
    }

    public Netnode(String id, String ossCode) {
        this.id = id;
        this.ossCode = ossCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public EquipmentModel getEquipModelId() {
        return equipModelId;
    }

    public void setEquipModelId(EquipmentModel equipModelId) {
        this.equipModelId = equipModelId;
    }

    public Station getStatID() {
        return statID;
    }

    public void setStatID(Station statID) {
        this.statID = statID;
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
        if (!(object instanceof Netnode)) {
            return false;
        }
        Netnode other = (Netnode) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.eman.basic.model.Netnode[ id=" + id + " ]";
    }

    @Override
    public String getLabel() {
        return ossCode+"|"+name;
    }
    
}
