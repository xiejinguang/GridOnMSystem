/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.peasant.security.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 谢金光
 */
@Entity
@Table(name = "sec_user_detail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SecUserDetail.findAll", query = "SELECT s FROM SecUserDetail s"),
    @NamedQuery(name = "SecUserDetail.findByUsername", query = "SELECT s FROM SecUserDetail s WHERE s.username = :username"),
    @NamedQuery(name = "SecUserDetail.findBySex", query = "SELECT s FROM SecUserDetail s WHERE s.sex = :sex"),
    @NamedQuery(name = "SecUserDetail.findByBirthday", query = "SELECT s FROM SecUserDetail s WHERE s.birthday = :birthday"),
    @NamedQuery(name = "SecUserDetail.findByFirstName", query = "SELECT s FROM SecUserDetail s WHERE s.firstName = :firstName"),
    @NamedQuery(name = "SecUserDetail.findByLastName", query = "SELECT s FROM SecUserDetail s WHERE s.lastName = :lastName")})
public class SecUserDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(nullable = false, length = 25)
    private String username;
    @Size(max = 45)
    @Column(length = 45)
    private String sex;
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthday;
    @Size(max = 45)
    @Column(length = 45)
    private String firstName;
    @Size(max = 45)
    @Column(length = 45)
    private String lastName;

    public SecUserDetail() {
    }

    public SecUserDetail(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SecUserDetail)) {
            return false;
        }
        SecUserDetail other = (SecUserDetail) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.peasant.security.model.SecUserDetail[ username=" + username + " ]";
    }
    
}
