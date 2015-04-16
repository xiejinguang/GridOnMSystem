/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eman.basic.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@DiscriminatorValue(value = "网络节点设备")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NetworkNodeModel.findAll", query = "SELECT e FROM NetworkNodeModel e"),
    @NamedQuery(name = "NetworkNodeModel.findById", query = "SELECT e FROM NetworkNodeModel e WHERE e.id = :id"),
    @NamedQuery(name = "NetworkNodeModel.findByType", query = "SELECT e FROM NetworkNodeModel e WHERE e.type = :type"),
    @NamedQuery(name = "NetworkNodeModel.findByClass1", query = "SELECT e FROM NetworkNodeModel e WHERE e.category = :category"),
    @NamedQuery(name = "NetworkNodeModel.findByNetType", query = "SELECT e FROM NetworkNodeModel e WHERE e.netType = :netType"),
    @NamedQuery(name = "NetworkNodeModel.findByModel", query = "SELECT e FROM NetworkNodeModel e WHERE e.model = :model"),
    @NamedQuery(name = "NetworkNodeModel.findByManufacturer", query = "SELECT e FROM NetworkNodeModel e WHERE e.manufacturer = :manufacturer")})
public class NetworkNodeModel extends EquipmentModel {

    @Basic(optional = false)
    @NotNull
    @Size(max = 45)
    @Column(nullable = false, length = 45)
    private String networkClass;//网络类型，Should be 3G,4G,……
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "equipModelId")
    private Collection<Netnode> netnodeCollection;

    @Basic(optional = true)
    @Size(max = 45)
    @Column(nullable = true, length = 45)
    private String supportedServiceType;//支持的业务类型,should be:语音，数据

    @Basic(optional = true)
    @Size(max = 45)
    @Column(nullable = true, length = 45)
    private String supportedStandards;//支持的标准,should be：CDMA1X，CDMA_EVDO,GSM,WCDMA……等值或组合值。

    /**
     * Get the value of supportedStandards
     *
     * @return the value of supportedStandards
     */
    public String getSupportedStandards() {
        return supportedStandards;
    }

    /**
     * Set the value of supportedStandards
     *
     * @param supportedStandards new value of supportedStandards
     */
    public void setSupportedStandards(String supportedStandards) {
        this.supportedStandards = supportedStandards;
    }

    /**
     * Get the value of supportedServiceType
     *
     * @return the value of supportedServiceType
     */
    public String getSupportedServiceType() {
        return supportedServiceType;
    }

    /**
     * Set the value of supportedServiceType
     *
     * @param supportedServiceType new value of supportedServiceType
     */
    public void setSupportedServiceType(String supportedServiceType) {
        this.supportedServiceType = supportedServiceType;
    }

    public NetworkNodeModel() {
        super();
    }

    public NetworkNodeModel(String id) {
        super(id);
    }

    public String getNetworkClass() {
        return networkClass;
    }

    public void setNetworkClass(String networkClass) {
        this.networkClass = networkClass;
    }

    @XmlTransient
    public Collection<Netnode> getNetnodeCollection() {
        return netnodeCollection;
    }

    public void setNetnodeCollection(Collection<Netnode> netnodeCollection) {
        this.netnodeCollection = netnodeCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    public static enum StandardType {

        CDMA_1X, CDMA_EVDO, LTE_FDD, LTE_TDD, GSM, WCDMA, TDSCDMA
    }

    public static enum ServiceType {

        VOICE, DATA, VOICE_OVER_IP,
    }

}
