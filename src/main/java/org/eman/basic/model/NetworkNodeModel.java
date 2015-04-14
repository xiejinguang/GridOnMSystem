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
@DiscriminatorValue(value = "网络主设备")
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
    private String netType;//网络类型，Should be 3G,4G
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "equipModelId")
    private Collection<Netnode> netnodeCollection;

    private String serviceType;

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

    public NetworkNodeModel() {
        super();
    }

    public NetworkNodeModel(String id) {
        super(id);
    }

    public String getNetType() {
        return netType;
    }

    public void setNetType(String netType) {
        this.netType = netType;
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
