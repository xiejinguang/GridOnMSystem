/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.eman.assit.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
//@Cacheable(false)
@Table(name = "asist_candidate_value")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AsistCandidateValue.findAll", query = "SELECT a FROM AsistCandidateValue a"),
    @NamedQuery(name = "AsistCandidateValue.findById", query = "SELECT a FROM AsistCandidateValue a WHERE a.id = :id"),
    @NamedQuery(name = "AsistCandidateValue.findByAccordingKey", query = "SELECT a FROM AsistCandidateValue a WHERE a.accordingKey = :accordingKey"),
    @NamedQuery(name = "AsistCandidateValue.findByValue", query = "SELECT a FROM AsistCandidateValue a WHERE a.value = :value")})
public class AsistCandidateValue implements Serializable, CandidateValue {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(nullable = false, length = 36)
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String accordingKey;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String value;
    @OneToMany(mappedBy = "parentID")
    private Collection<AsistCandidateValue> asistCandidateValueCollection;
    @JoinColumn(name = "parentID", referencedColumnName = "id")
    @ManyToOne
    private AsistCandidateValue parentID;

    public AsistCandidateValue() {
    }

    public AsistCandidateValue(String id) {
        this.id = id;
    }

    public AsistCandidateValue(String id, String accordingKey, String value) {
        this.id = id;
        this.accordingKey = accordingKey;
        this.value = value;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getAccordingKey() {
        return accordingKey;
    }

    @Override
    public void setAccordingKey(String accordingKey) {
        this.accordingKey = accordingKey;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @XmlTransient
    public Collection<AsistCandidateValue> getAsistCandidateValueCollection() {
        return asistCandidateValueCollection;
    }


    public void setAsistCandidateValueCollection(Collection<AsistCandidateValue> asistCandidateValueCollection) {
        this.asistCandidateValueCollection = asistCandidateValueCollection;
    }

   
    public AsistCandidateValue getParentID() {
        return parentID;
    }

   
    public void setParentID(AsistCandidateValue parentID) {
        this.parentID = parentID;
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
        if (!(object instanceof AsistCandidateValue)) {
            return false;
        }
        AsistCandidateValue other = (AsistCandidateValue) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.eman.assit.model.AsistCandidateValue[ id=" + id + " ]";
    }

    @Override
    public Collection<CandidateValue> getChildren() {
        Collection<? extends CandidateValue> children =null;
        children = asistCandidateValueCollection;
        return (Collection<CandidateValue>)children;
    }

    @Override
    public CandidateValue getParent() {
        return this.getParentID();
    }

    @Override
    public void setChildren(Collection<CandidateValue> candidateValueCollection) {
        Collection<? extends CandidateValue> children =candidateValueCollection;        
        this.setAsistCandidateValueCollection((Collection<AsistCandidateValue>)children);
    }

    @Override
    public void setParent(CandidateValue parentID) {
        this.setParentID((AsistCandidateValue)parentID);
    }
    
}
