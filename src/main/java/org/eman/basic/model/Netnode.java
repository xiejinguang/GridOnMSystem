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
import org.peasant.jpa.DatedEntity;
import org.peasant.jpa.Labeled;
import org.peasant.jpa.UUIDEntity;

/**
 *
 * @author 谢金光
 */
@Entity
@Table(catalog = "jobpromotion", schema = "", name = "basic_netnode", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"ossCode"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Netnode.findAll", query = "SELECT n FROM Netnode n"),
    @NamedQuery(name = "Netnode.findById", query = "SELECT n FROM Netnode n WHERE n.id = :id"),
    @NamedQuery(name = "Netnode.findByOssCode", query = "SELECT n FROM Netnode n WHERE n.ossCode = :ossCode"),
    @NamedQuery(name = "Netnode.findByName", query = "SELECT n FROM Netnode n WHERE n.name = :name"),
    @NamedQuery(name = "Netnode.findByInvestTime", query = "SELECT n FROM Netnode n WHERE n.investTime = :investTime"),
    @NamedQuery(name = "Netnode.findByStatus", query = "SELECT n FROM Netnode n WHERE n.status = :status")})
public class Netnode extends DatedEntity implements Serializable, Labeled {

    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String ossCode;

    @Basic(optional = false)
    @Size(max = 45)
    @Column(nullable = false, length = 45)
    private String name;
    @Basic(optional = false)
    @Size(min = 1, max = 25)
    @NotNull
    @Column(nullable = false, length = 25)
    private String nodeType;

    @Basic(optional = false)
    @Size(min = 1, max = 25)
    @NotNull
    @Column(nullable = false, length = 25)
    private String network;

    @Size(max = 45)
    @Column(length = 45)
    private String status;

    @JoinColumn(name = "equipModelId", referencedColumnName = "id", nullable = true)
    @ManyToOne(optional = true)
    private NetworkNodeModel equipModelId;

    @JoinColumn(name = "roomspotId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Roomspot roomspot;

    @Temporal(TemporalType.DATE)
    @Column(nullable = true)
    private Date startProductionTime;

    @Basic(optional = false)
    @Size(min = 1, max = 25)
    @NotNull
    @Column(nullable = false, length = 25)
    private String serviceType;

    @Temporal(TemporalType.DATE)
    private Date investTime;

    @Lob
    @Size(max = 65535)
    @Column(length = 65535)
    private String commont;

    
    private String superior;

    /**
     * Get the value of superior
     *
     * @return the value of superior
     */
    public String getSuperior() {
        return superior;
    }

    /**
     * Set the value of superior
     *
     * @param superior new value of superior
     */
    public void setSuperior(String superior) {
        this.superior = superior;
    }

    /**
     * Get the value of network
     *
     * @return the value of network
     */
    public String getNetwork() {
        return network;
    }

    /**
     * Set the value of network
     *
     * @param network new value of network
     */
    public void setNetwork(String network) {
        this.network = network;
    }

    /**
     * Get the value of nodeType
     *
     * @return the value of nodeType
     */
    public String getNodeType() {
        return nodeType;
    }

    /**
     * Set the value of nodeType
     *
     * @param nodeType new value of nodeType
     */
    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    /**
     * Get the value of serviceType
     *
     * @return the value of serviceType
     */
    public String getServiceType() {
        return serviceType;
    }

    /**
     * Set the value of serviceType
     *
     * @param serviceType new value of serviceType
     */
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    /**
     * Get the value of startProductionTime
     *
     * @return the value of startProductionTime
     */
    public Date getStartProductionTime() {
        return startProductionTime;
    }

    /**
     * Set the value of startProductionTime
     *
     * @param startProductionTime new value of startProductionTime
     */
    public void setStartProductionTime(Date startProductionTime) {
        this.startProductionTime = startProductionTime;
    }

    public Netnode() {
    }

    public Netnode(String id) {
        super(id);
    }

    public Netnode(String id, String ossCode) {
        this(id);
        this.ossCode = ossCode;
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

    public NetworkNodeModel getEquipModelId() {
        return equipModelId;
    }

    public void setEquipModelId(NetworkNodeModel equipModelId) {
        this.equipModelId = equipModelId;
    }

    public Roomspot getRoomspot() {
        return roomspot;
    }

    public void setRoomspot(Roomspot roomspot) {
        this.roomspot = roomspot;
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
        return ossCode + "|" + name;
    }

    public static enum NetnodeStatus {

        ACTIVATED,//激活服务
        READY_DEACTIVATED,//开通未激活服务
        DATA_ADDED,//数据添至网络
        DATA_REMOVED,//数据从网络中删除
        DESTROYED,//设备拆除
        UNKNOWN//未知
    }
}
