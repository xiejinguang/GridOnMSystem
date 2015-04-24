/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eman.basic.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.peasant.jpa.DatedEntity;
import org.peasant.jpa.Labeled;

/**
 *
 * @author 谢金光
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "nodeType", discriminatorType = DiscriminatorType.STRING)
@Table(name = "basic_netnode", uniqueConstraints = {
    @UniqueConstraint(name = "UNQ_basic_netnode_0", columnNames = {"ossCode"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Netnode.findAll", query = "SELECT n FROM Netnode n"),
    @NamedQuery(name = "Netnode.findById", query = "SELECT n FROM Netnode n WHERE n.uuid = :uuid"),
    @NamedQuery(name = "Netnode.findByOssCode", query = "SELECT n FROM Netnode n WHERE n.ossCode = :ossCode"),
    @NamedQuery(name = "Netnode.findByName", query = "SELECT n FROM Netnode n WHERE n.name = :name"),
    @NamedQuery(name = "Netnode.findByInvestTime", query = "SELECT n FROM Netnode n WHERE n.investTime = :investTime"),
    @NamedQuery(name = "Netnode.findByStatus", query = "SELECT n FROM Netnode n WHERE n.status = :status")})
public class Netnode extends DatedEntity implements Serializable, Labeled {

    private static final long serialVersionUID = 1L;
    /**
     * OSS系统对应的网元号
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String ossCode;
    /**
     * 网管中的网元ID序号
     */
    @Basic(optional = false)
    @Min(0)
    @Column(nullable = false)
    private Integer nodeId;
    /**
     * 网管中的网元名称
     */
    @Basic(optional = false)
    @Size(max = 45)
    @Column(nullable = false, length = 45)
    private String name;
    /**
     * 网元类型，可能的值是BTS、BSC、RRU、MSC、eNode、MME……
     */
    @Basic(optional = false)
    @Size(min = 1, max = 25)
    @NotNull
    @Column(nullable = false, length = 25)
    private String nodeType;
    /**
     * 网元归属的网络名称，可能的值是村村通、CDMA、LTE
     */
    @Basic(optional = false)
    @Size(min = 1, max = 25)
    @NotNull
    @Column(nullable = false, length = 25)
    private String network;
    /**
     * 网元的状态
     */
    @Size(max = 45)
    @Column(length = 45)
    @Enumerated(EnumType.STRING)
    private NetnodeStatus status;
    /**
     * 网元的投产时间
     */
    @Temporal(TemporalType.DATE)
    @Column(nullable = true)
    private Date startProductionTime;
    /**
     * 提供的服务类型
     */
    @Basic(optional = true)
    @Size(min = 1, max = 25)    
    @Column(nullable = true, length = 25)
    private String serviceType;
    /**
     * OSS系统对的网元编码
     */
    @Temporal(TemporalType.DATE)
    private Date investTime;
    /**
     * 备注
     */
    @Lob
    @Size(max = 65535)
    @Column(length = 65535)
    private String commont;
    /**
     * 父网元
     */
    @ManyToOne(optional = true)
    @JoinColumn(name = "superiorId")
    private Netnode superior;

    /**
     * 子网元集合
     */
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "superior")
    private Collection<Netnode> subordinates;

    /**
     * 网元的等级
     */
    @NotNull
    @Basic(optional = false)
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Grade grade = Grade.UNKNOWN;
    /**
     * 网元的设备模型
     */
    @JoinColumn(name = "equipModelId", referencedColumnName = "uuid")
    @ManyToOne
    private NetworkNodeModel equipModelId;

    /**
     * 网元的安装机房、站点
     */
    @JoinColumn(name = "roomspotId", referencedColumnName = "uuid", nullable = false)
    @ManyToOne(optional = false)
    private Roomspot roomspot;

    /**
     * Get the value of grade
     *
     * @return the value of grade
     */
    public Grade getGrade() {
        return grade;
    }

    /**
     * Set the value of grade
     *
     * @param grade new value of grade
     */
    public void setGrade(Grade grade) {
        this.grade = grade;
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

    /**
     * Get the value of nodeId
     *
     * @return the value of nodeId
     */
    public Integer getNodeId() {
        return nodeId;
    }

    /**
     * Set the value of nodeId
     *
     * @param nodeId new value of nodeId
     */
    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    /**
     * Get the value of superior
     *
     * @return the value of superior
     */
    public Netnode getSuperior() {
        return superior;
    }

    /**
     * Set the value of superior
     *
     * @param superior new value of superior
     */
    public void setSuperior(Netnode superior) {
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

    public NetnodeStatus getStatus() {
        return status;
    }

    public void setStatus(NetnodeStatus status) {
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

    public Collection<Netnode> getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(Collection<Netnode> subordinates) {
        this.subordinates = subordinates;
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

    public static enum Grade {

        VIP, A, B, C, D, E, F, G, UNKNOWN
    }
}
