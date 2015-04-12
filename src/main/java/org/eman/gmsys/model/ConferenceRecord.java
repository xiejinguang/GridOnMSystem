package org.eman.gmsys.model;

import org.peasant.jpa.UUIDEntity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(catalog = "jobpromotion", schema = "",name = "cm_conference_record")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dType")
@DiscriminatorValue(value = "通用会议记录")
public class ConferenceRecord extends UUIDEntity implements Serializable {

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
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String title;

    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private String content;

    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private String department;

    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private String emcee;

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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmcee() {
        return emcee;
    }

    public void setEmcee(String emcee) {
        this.emcee = emcee;
    }

}
