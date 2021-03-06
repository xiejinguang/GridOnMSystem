/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.ORGStructure.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import javax.persistence.Basic;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.MapKeyColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.SecondaryTables;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.peasant.basic.model.Address;
import org.peasant.jpa.DatedEntity;

/**
 *
 * @author 谢金光
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"idcardNum"}),
    @UniqueConstraint(columnNames = {"code"})})

@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Employee.findAll", query = "SELECT e FROM Employee e"),
    @NamedQuery(name = "Employee.findById", query = "SELECT e FROM Employee e WHERE e.uuid = :uuid"),
    @NamedQuery(name = "Employee.findByCode", query = "SELECT e FROM Employee e WHERE e.code = :code"),
    @NamedQuery(name = "Employee.findByName", query = "SELECT e FROM Employee e WHERE e.name = :name"),
    @NamedQuery(name = "Employee.findBySex", query = "SELECT e FROM Employee e WHERE e.sex = :sex"),
    @NamedQuery(name = "Employee.findByBirthday", query = "SELECT e FROM Employee e WHERE e.birthday = :birthday"),
    @NamedQuery(name = "Employee.findByIdcardNum", query = "SELECT e FROM Employee e WHERE e.idcardNum = :idcardNum"),
    @NamedQuery(name = "Employee.findByMobilePhoneNum", query = "SELECT e FROM Employee e WHERE e.mobilePhoneNum = :mobilePhoneNum")})
public class Employee extends DatedEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(nullable = false, length = 36)
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(nullable = false, length = 10)
    private String code;
    @Size(max = 45)
    @Column(length = 45)
    private String name;
    @Size(max = 2)
    @Column(length = 2)
    private String sex;
    @Size(max = 45)
    @Column(length = 45)
    private String birthday;
    @Size(max = 45)
    @Column(length = 45)
    private String idcardNum;
    @Size(max = 11)
    @Column(length = 11)
    private String mobilePhoneNum;

    @Embedded
    @ElementCollection
    @CollectionTable(name = "addresses")
    private Map<String, Address> addresses;

    @Embedded
    @ElementCollection
    @CollectionTable(name = "addressHistory")
    private Collection<Address> addressHistory;
    @Embedded
    private Address majorAddress;

    @ElementCollection
    private Collection<String> nicknames;

    /**
     * Get the value of nicknames
     *
     * @return the value of nicknames
     */
    public Collection<String> getNicknames() {
        return nicknames;
    }

    /**
     * Set the value of nicknames
     *
     * @param nicknames new value of nicknames
     */
    public void setNicknames(Collection<String> nicknames) {
        this.nicknames = nicknames;
    }

    /**
     * Get the value of majorAddress
     *
     * @return the value of majorAddress
     */
    public Address getMajorAddress() {
        return majorAddress;
    }

    /**
     * Set the value of majorAddress
     *
     * @param majorAddress new value of majorAddress
     */
    public void setMajorAddress(Address majorAddress) {
        this.majorAddress = majorAddress;
    }

    /**
     * Get the value of addresses
     *
     * @return the value of addresses
     */
    public Map<String, Address> getAddresses() {
        return addresses;
    }

    /**
     * Set the value of addresses
     *
     * @param addresses new value of addresses
     */
    public void setAddresses(Map<String, Address> addresses) {
        this.addresses = addresses;
    }

    public Employee() {
    }

    public Employee(String id) {
        this.id = id;
    }

    public Employee(String id, String code) {
        this.id = id;
        this.code = code;
    }



    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getIdcardNum() {
        return idcardNum;
    }

    public void setIdcardNum(String idcardNum) {
        this.idcardNum = idcardNum;
    }

    public String getMobilePhoneNum() {
        return mobilePhoneNum;
    }

    public void setMobilePhoneNum(String mobilePhoneNum) {
        this.mobilePhoneNum = mobilePhoneNum;
    }

    public Collection<Address> getAddress() {
        return addressHistory;
    }

    public void setAddress(Collection<Address> address) {
        this.addressHistory = address;
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
        if (!(object instanceof Employee)) {
            return false;
        }
        Employee other = (Employee) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.peasant.ORGStructure.model.Employee[ id=" + id + " ]";
    }

}
