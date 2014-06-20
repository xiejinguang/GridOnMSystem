/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.peasant.security.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author 谢金光
 */
@Entity
@Table(name = "sec_user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username"),
    @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password"),
    @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
    @NamedQuery(name = "User.findByCreateTime", query = "SELECT u FROM User u WHERE u.createTime = :createTime"),
    @NamedQuery(name = "User.findBySaltMod", query = "SELECT u FROM User u WHERE u.saltMod = :saltMod"),
    @NamedQuery(name = "User.findBySaltPassword", query = "SELECT u FROM User u WHERE u.saltPassword = :saltPassword")})
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(nullable = false, length = 25)
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(nullable = false, length = 32)
    private String password;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="电子邮件无效")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 255)
    @Column(length = 255)
    private String email;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Lob
    private byte[] salt;
    @Size(max = 45)
    @Column(length = 45)
    private String saltMod;
    @Size(max = 45)
    @Column(length = 45)
    private String saltPassword;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "username")
    private Collection<UserRole> userRoleCollection;

    public User() {
    }

    public User(String username) {
        this.username = username;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public String getSaltMod() {
        return saltMod;
    }

    public void setSaltMod(String saltMod) {
        this.saltMod = saltMod;
    }

    public String getSaltPassword() {
        return saltPassword;
    }

    public void setSaltPassword(String saltPassword) {
        this.saltPassword = saltPassword;
    }

    @XmlTransient
    public Collection<UserRole> getUserRoleCollection() {
        return userRoleCollection;
    }

    public void setUserRoleCollection(Collection<UserRole> userRoleCollection) {
        this.userRoleCollection = userRoleCollection;
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
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.peasant.security.model.User[ username=" + username + " ]";
    }
    
}
