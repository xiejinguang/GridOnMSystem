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
public class Address implements Serializable {

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

}
