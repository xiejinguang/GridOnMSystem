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
    String owner;
    InputStream is;
    int size = -1;
    private Date uploadedTime;

    DefaultAttachment() {
        this(UUID.randomUUID().toString(),null,null,null,null,Calendar.getInstance().getTime());

    }

    DefaultAttachment(String id, String contentType, String name, String owner, InputStream is,Date uploadDate) {
        if (null == id) {
            this.id = UUID.randomUUID().toString();
        }
        this.contentType = contentType;
        this.name = name;
        this.owner = owner;
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

    @Override
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getOwner() {
        return this.owner;
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
    public void setOwner(String owner) {
        this.owner = owner;
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

}
