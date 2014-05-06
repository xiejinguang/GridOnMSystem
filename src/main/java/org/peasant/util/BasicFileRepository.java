/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Singleton;
import javax.faces.context.FacesContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;

/**
 *
 * @author 谢金光
 */
@Singleton
public class BasicFileRepository implements Repository {

    public final static String DEFAULT_ATTACH_DIRECTORY = "attachments";
    public final static String PARAM_ATTACHMENT_HOME_DIRECTORY = "pleasant.util.REPOSITORY_PATH";

    public final String repositoryPath;
    public final File repoDirectory;
    private static Repository defaultInstance = null;

    public static Repository getDefault() {
        return (null != defaultInstance) ? defaultInstance : new BasicFileRepository();
    }

    public BasicFileRepository() {
        this((!System.getProperty("user.dir").endsWith(File.separator)
                ? System.getProperty("user.dir") : System.getProperty("user.dir").substring(0, System.getProperty("user.dir").length() - 1))
                + File.separator + DEFAULT_ATTACH_DIRECTORY);

    }

    /**
     *
     * @param homePath must be a abstract pathname,see java.io.File for details;
     */
    public BasicFileRepository(String homePath) {
        File homeDir = new File(homePath);
        if (homeDir.exists()) {
            if (!homeDir.isDirectory()) {
                throw new IllegalArgumentException("homePath must be a directory");
            }
        } else if (!homeDir.mkdirs()) {
            throw new RuntimeException("Can't create the Directory specified by homePath argument");
        }
        repositoryPath = homePath;
        repoDirectory = homeDir;

    }

    @Override
    public Attachment getAttachment(String aId) {
        return null;//TODO
    }

    @Override
    public List<Attachment> getAllAttachments() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Attachment> getAttachmentsByOwner(String owner) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(String aId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Attachment a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InputStream getInputStream(String aId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<InputStream> getInputStreams(String owner) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OutputStream getOutputStream(String aId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Attachment createAttachment() {
        return new DefaultAttachment();
    }

    /**
     *
     * @param name the value of name
     * @param owner the value of owner
     * @param contentType the value of contentType
     * @param uploadTime the value of uploadTime
     * @param par4 the value of par4
     */
    @Override
    public Attachment createAttachment(String name, String owner, String contentType, Date uploadTime, InputStream is) {
        return new DefaultAttachment(UUID.randomUUID().toString(), contentType, name, owner, is, uploadTime);
    }

    /**
     *
     * @param inputStream the value of inputStream
     * @param name the value of name
     * @param contentType the value of contentType
     * @param uploadTime the value of uploadTime
     * @param owner the value of owner
     */
    @Override
    public String storeFromStream(InputStream inputStream, String name, String contentType, String owner, Date uploadTime) {
        Attachment a = createAttachment(name, owner, contentType, uploadTime, inputStream);
        return store(a);
    }

    @Override
    public String store(Attachment a) {

        if (null == getAttachment(a.getID())) {

            if (null == a.getInputStream()) {
                throw new IllegalArgumentException("The InputStream of Attachment could not be null!");
            }
            String filename = a.getName();
            String fName = filename;
            String fType = "";
            if (filename.lastIndexOf(".") != -1) {
                fName = filename.substring(0, filename.lastIndexOf("."));
                fType = filename.substring(filename.lastIndexOf(".") + 1);
            }
            a.setUploadTime(Calendar.getInstance().getTime());
            
            String date = String.format("%1$tY-%1$tm-%1$td",a.getUploadTime());
            String year = String.format("%1$tY", a.getUploadTime());
            String subDir = year + "/" + date;
            File afolder = new File(repoDirectory, subDir);
            if ((!afolder.exists())&&(!afolder.mkdirs())) {
                throw new RuntimeException("Can't create the attachment folder");
            }
            Path af = java.nio.file.Paths.get(repositoryPath,subDir,filename);
            int i = 1;
            while (Files.exists(af)) {
                filename = fName + "(" + i + ")." + fType;
                i++;
                af = java.nio.file.Paths.get(repositoryPath,subDir,filename);
            }
            String relPath= subDir+"/"+filename;
            try {
                Files.copy(a.getInputStream(), af, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                a.getInputStream().close();
            } catch (IOException ex) {
                Logger.getLogger(BasicFileRepository.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }

        }
        return null;
    }

}
