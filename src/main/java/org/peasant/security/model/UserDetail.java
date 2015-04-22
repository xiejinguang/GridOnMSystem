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
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
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
    @NamedQuery(name = "UserDetail.findAll", query = "SELECT u FROM UserDetail u"),
    @NamedQuery(name = "UserDetail.findByUsername", query = "SELECT u FROM UserDetail u WHERE u.username = :username"),
    @NamedQuery(name = "UserDetail.findBySex", query = "SELECT u FROM UserDetail u WHERE u.sex = :sex"),
    @NamedQuery(name = "UserDetail.findByBirthday", query = "SELECT u FROM UserDetail u WHERE u.birthday = :birthday"),
    @NamedQuery(name = "UserDetail.findByFirstName", query = "SELECT u FROM UserDetail u WHERE u.firstName = :firstName"),
    @NamedQuery(name = "UserDetail.findByLastName", query = "SELECT u FROM UserDetail u WHERE u.lastName = :lastName")})
public class UserDetail implements Serializable {
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
    @JoinColumn(name = "username", referencedColumnName = "username", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private User user;

    public UserDetail() {
    }

    public UserDetail(String username) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        if (!(object instanceof UserDetail)) {
            return false;
        }
        UserDetail other = (UserDetail) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.peasant.security.model.UserDetail[ username=" + username + " ]";
    }
    
}
