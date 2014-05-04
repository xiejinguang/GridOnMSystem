/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.util;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Remote;

/**
 *
 * @author 谢金光
 */
@Local
@Remote
public interface Repository {

    Attachment getAttachment(String aId);

    List<Attachment> getAllAttachments();

    List<Attachment> getAttachmentsByOwner(String owner);

    boolean delete(String aId);

    boolean delete(Attachment a);

    boolean deleteAll();

    InputStream getInputStream(String aId);

    List<InputStream> getInputStreams(String owner);

    OutputStream getOutputStream(String aId);

    Attachment createAttachment();

    /**
     *
     * @param name the value of name
     * @param owner the value of owner
     * @param contentType the value of contentType
     * @param uploadTime the value of uploadTime
     * @param is the value of inputStream
     */
    Attachment createAttachment(String name, String owner, String contentType, Date uploadTime, InputStream is);

    /**
     *
     * @param inputStream the value of inputStream
     * @param name the value of name
     * @param contentType the value of contentType
     * @param uploadTime the value of uploadTime
     * @param owner the value of owner
     */
    String storeFromStream(InputStream inputStream, String name, String contentType, String owner, Date uploadTime);

    String store(Attachment a);
}
