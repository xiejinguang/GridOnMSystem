/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.eman.asist.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 谢金光
 */
@Entity
@Table(name = "asist_key_generator")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KeyGenerator.findAll", query = "SELECT k FROM KeyGenerator k"),
    @NamedQuery(name = "KeyGenerator.findByDiscriminator", query = "SELECT k FROM KeyGenerator k WHERE k.discriminator = :discriminator"),
    @NamedQuery(name = "KeyGenerator.findByNumber", query = "SELECT k FROM KeyGenerator k WHERE k.number = :number")})
public class KeyGenerator implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String discriminator;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int number;

    public KeyGenerator() {
    }

    public KeyGenerator(String discriminator) {
        this.discriminator = discriminator;
    }

    public KeyGenerator(String discriminator, int number) {
        this.discriminator = discriminator;
        this.number = number;
    }

    public String getDiscriminator() {
        return discriminator;
    }

    public void setDiscriminator(String discriminator) {
        this.discriminator = discriminator;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (discriminator != null ? discriminator.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KeyGenerator)) {
            return false;
        }
        KeyGenerator other = (KeyGenerator) object;
        if ((this.discriminator == null && other.discriminator != null) || (this.discriminator != null && !this.discriminator.equals(other.discriminator))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.eman.asist.model.KeyGenerator[ discriminator=" + discriminator + " ]";
    }
    
}
