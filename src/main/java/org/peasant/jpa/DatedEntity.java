/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.jpa;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author 谢金光
 */
@MappedSuperclass
public abstract class DatedEntity extends UUIDEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "create_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Column(name = "last_update", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;

    public DatedEntity() {
    }

    public DatedEntity(String uuid) {
        super(uuid);

    }

    public DatedEntity(String uuid, Date createTime) {
        super(uuid);
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    

    @PrePersist
    public void fixCreateTime() {
        this.createTime = Calendar.getInstance().getTime();
        this.lastUpdate = Calendar.getInstance().getTime();
    }

    @PreUpdate
    public void fixLastUpdateTime() {
        this.lastUpdate = Calendar.getInstance().getTime();

    }

}
