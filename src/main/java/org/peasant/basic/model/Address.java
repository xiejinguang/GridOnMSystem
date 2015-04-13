/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.basic.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.peasant.jpa.UUIDEntity;

/**
 *
 * @author 谢金光
 */

@Embeddable
@XmlRootElement
public class Address extends UUIDEntity implements Serializable {

    @Size(max = 45)
    @Column(length = 45)
    private String country;
    @Size(max = 45)
    @Column(length = 45)
    private String province;
    @Size(max = 45)
    @Column(length = 45)
    private String city;
    @Size(max = 45)
    @Column(length = 45)
    private String county;
    @Size(max = 255)
    @Column(length = 255)
    private String address;

    public Address() {
        super();
    }

    public Address(String id) {
       super(id);
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
        if (!(object instanceof Address)) {
            return false;
        }
        Address other = (Address) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.peasant.basic.model.Address[ id=" + id + " ]";
    }

}
