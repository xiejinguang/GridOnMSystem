/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.basic.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.peasant.jpa.UUIDEntity;

/**
 *
 * @author 谢金光
 */
@Entity
@Table(name = "article_category", catalog = "jobpromotion", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"name"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ArticleCategory.findAll", query = "SELECT a FROM ArticleCategory a"),
    @NamedQuery(name = "ArticleCategory.findById", query = "SELECT a FROM ArticleCategory a WHERE a.id = :id"),
    @NamedQuery(name = "ArticleCategory.findByName", query = "SELECT a FROM ArticleCategory a WHERE a.name = :name")})
public class ArticleCategory extends UUIDEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String name;
    @Lob
    @Size(max = 65535)
    @Column(length = 65535)
    private String description;
    @OneToMany(mappedBy = "category",cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.DETACH})
    private Collection<Article> articleCollection;
    @OneToMany(mappedBy = "superior")
    private Collection<ArticleCategory> articleCategoryCollection;
    @JoinColumn(name = "superior", referencedColumnName = "id")
    @ManyToOne
    private ArticleCategory superior;

    public ArticleCategory() {
        super();
    }

    public ArticleCategory(String id) {
        super(id);
    }

    public ArticleCategory(String id, String name) {
        super(id);
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public Collection<Article> getArticleCollection() {
        return articleCollection;
    }

    public void setArticleCollection(Collection<Article> articleCollection) {
        this.articleCollection = articleCollection;
    }

    @XmlTransient
    public Collection<ArticleCategory> getArticleCategoryCollection() {
        return articleCategoryCollection;
    }

    public void setArticleCategoryCollection(Collection<ArticleCategory> articleCategoryCollection) {
        this.articleCategoryCollection = articleCategoryCollection;
    }

    public ArticleCategory getSuperior() {
        return superior;
    }

    public void setSuperior(ArticleCategory superior) {
        this.superior = superior;
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
        if (!(object instanceof ArticleCategory)) {
            return false;
        }
        ArticleCategory other = (ArticleCategory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.peasant.basic.model.ArticleCategory[ id=" + id + " ]";
    }
    
}
