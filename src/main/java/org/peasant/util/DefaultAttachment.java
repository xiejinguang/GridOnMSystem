/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.util;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author 谢金光
 */
public class DefaultAttachment implements Attachment {

    String id;
    String contentType;
    String name;
    String belonger;
    InputStream is;
    String attacher;
    int size = -1;
    private Date uploadedTime;

   public DefaultAttachment() {
        this(UUID.randomUUID().toString(), null, null, null, null, Calendar.getInstance().getTime());

    }

   public DefaultAttachment(String id, String contentType, String name, String owner, InputStream is, Date uploadDate) {
        if (null == id) {
            this.id = UUID.randomUUID().toString();
        }
        this.contentType = contentType;
        this.name = name;
        this.belonger = owner;
        this.is = is;
        this.uploadedTime = uploadDate;
    }

    @Override
    public String getID() {
        if (null == this.id) {
            this.id = UUID.randomUUID().toString();
        }
        return this.id;
    }
        public void setID(String id) {

        this.id = id;
    }

    @Override
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getBelonger() {
        return this.belonger;
    }

    @Override
    public Date getUploadTime() {
        return this.uploadedTime;
    }

    @Override
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public void setBelonger(String owner) {
        this.belonger = owner;
    }

    @Override
    public void setUploadTime(Date uploadTime) {
        this.uploadedTime = uploadTime;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public InputStream getInputStream() {
        return this.is;
    }

    @Override
    public void setInputStream(InputStream is) {
        this.is = is;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public void setAttacher(String attacher) {
        this.attacher = attacher;
    }

    @Override
    public String getAttacher() {
        return this.attacher;
    }

    @Override
    public void setSize(int size) {
        this.size = size;
    }

}
