/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gmsys.model.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Attachment.findAll", query = "SELECT a FROM Attachment a"),
    @NamedQuery(name = "Attachment.findByAttachmentId", query = "SELECT a FROM Attachment a WHERE a.attachmentId = :attachmentId"),
    @NamedQuery(name = "Attachment.findByName", query = "SELECT a FROM Attachment a WHERE a.name = :name"),
    @NamedQuery(name = "Attachment.findByBelonger", query = "SELECT a FROM Attachment a WHERE a.belonger = :belonger"),
    @NamedQuery(name = "Attachment.findByContentType", query = "SELECT a FROM Attachment a WHERE a.contentType = :contentType"),
    @NamedQuery(name = "Attachment.findByUploadTime", query = "SELECT a FROM Attachment a WHERE a.uploadTime = :uploadTime"),
    @NamedQuery(name = "Attachment.findByRelPath", query = "SELECT a FROM Attachment a WHERE a.relPath = :relPath"),
    @NamedQuery(name = "Attachment.findByAttacher", query = "SELECT a FROM Attachment a WHERE a.attacher = :attacher")})
public class Attachment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    private String attachmentId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    private String name;
    @Size(max = 45)
    private String belonger;
    @Size(max = 20)
    private String contentType;
    @Temporal(TemporalType.DATE)
    private Date uploadTime;
    @Size(max = 255)
    private String relPath;
    @Size(max = 45)
    private String attacher;

    public Attachment() {
    }

    public Attachment(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public Attachment(String attachmentId, String name) {
        this.attachmentId = attachmentId;
        this.name = name;
    }

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBelonger() {
        return belonger;
    }

    public void setBelonger(String belonger) {
        this.belonger = belonger;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getRelPath() {
        return relPath;
    }

    public void setRelPath(String relPath) {
        this.relPath = relPath;
    }

    public String getAttacher() {
        return attacher;
    }

    public void setAttacher(String attacher) {
        this.attacher = attacher;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (attachmentId != null ? attachmentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Attachment)) {
            return false;
        }
        Attachment other = (Attachment) object;
        if ((this.attachmentId == null && other.attachmentId != null) || (this.attachmentId != null && !this.attachmentId.equals(other.attachmentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.gmsys.view.util.Attachment[ attachmentId=" + attachmentId + " ]";
    }
    
}
