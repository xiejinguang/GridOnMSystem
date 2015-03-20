/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.basic.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.peasant.model.DatedEntity;
import org.peasant.model.UUIDEntity;

/**
 *
 * @author 谢金光
 */
@Entity
@Table(name = "article", catalog = "jobpromotion", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Article.findAll", query = "SELECT a FROM Article a"),
    @NamedQuery(name = "Article.findById", query = "SELECT a FROM Article a WHERE a.id = :id"),
    @NamedQuery(name = "Article.findByTitle", query = "SELECT a FROM Article a WHERE a.title = :title"),
    @NamedQuery(name = "Article.findByKeywords", query = "SELECT a FROM Article a WHERE a.keywords = :keywords"),
    @NamedQuery(name = "Article.findByCreator", query = "SELECT a FROM Article a WHERE a.creator = :creator"),
    @NamedQuery(name = "Article.findByCreateTime", query = "SELECT a FROM Article a WHERE a.createTime = :createTime"),
    @NamedQuery(name = "Article.findByLastUpdate", query = "SELECT a FROM Article a WHERE a.lastUpdate = :lastUpdate")})
public class Article extends DatedEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String title;
    @Size(max = 255)
    @Column(length = 255)
    private String keywords;
    @Lob
    @Size(max = 65535)
    @Column(length = 65535)
    private String digest;
    @Lob
    @Size(max = 65535)
    @Column(length = 65535)
    private String description;
    @Lob
    @Size(max = 16777215)
    @Column(length = 16777215)
    private String content;
    @Size(max = 36)
    @Column(length = 36)
    private String creator;

    @JoinColumn(name = "category", referencedColumnName = "id")
    @ManyToOne
    private ArticleCategory category;

    public Article() {
        super();
    }

    public Article(String id) {
        super(id);
    }

    public Article(String id, String title, Date createTime) {
        super(id, createTime);
        this.title = title;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public ArticleCategory getCategory() {
        return category;
    }

    public void setCategory(ArticleCategory category) {
        this.category = category;
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
        if (!(object instanceof Article)) {
            return false;
        }
        Article other = (Article) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.peasant.basic.model.Article[ id=" + id + " ]";
    }

}
