/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 *
 * @author 谢金光
 */
public interface Attachment {

    /**
     *
     * @return
     */
    public String getID();

    /**
     *
     * @return
     */
    public String getContentType();

    /**
     *
     * @return
     */
    public String getName();

    /**
     *
     * @return
     */
    public String getBelonger();

    /**
     *
     * @return
     */
    public Date getUploadTime();

    /**
     *
     * @param contentType
     */
    public void setContentType(String contentType);

    /**
     *
     * @param owner whow owns this Attachment
     */
    public void setBelonger(String owner);

    /**
     *
     * @param uploadTime
     */
    public void setUploadTime(Date uploadTime);

    /**
     *
     * @param name
     */
    public void setName(String name);

    /**
     *
     * @return a new InputStream that you can read data from it.
     */
    public InputStream getInputStream()throws IOException;
    
    /**
     *
     */
    public void setInputStream(InputStream is);
    
    /**
     *
     * @return
     */
    public int getSize();
    
    public void setSize(int size);
    
    /**
     *
     * @param attacher The user who attach this Attachment.
     */
    public void setAttacher(String attacher);
    public String getAttacher();

}
