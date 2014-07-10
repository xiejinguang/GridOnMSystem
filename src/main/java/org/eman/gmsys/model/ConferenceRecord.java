package org.eman.gmsys.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "cm_conference_record")
public class ConferenceRecord extends UUIDIdentity implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date cdate;

    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createDate;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1,max = 255)    
    @Column(nullable = false, length = 255)
    private String title;

    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private String content;

    public Date getCdate() {
        return cdate;
    }

    public void setCdate(Date cdate) {
        this.cdate = cdate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
