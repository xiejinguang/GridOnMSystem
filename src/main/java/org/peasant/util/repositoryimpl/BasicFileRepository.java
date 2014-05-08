/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.util.repositoryimpl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.peasant.util.Attachment;
import org.peasant.util.DefaultAttachment;
import org.peasant.util.GenericFacade;
import org.peasant.util.Repository;

/**
 *
 * @author 谢金光
 */
@Singleton
public class BasicFileRepository extends GenericFacade<DBAttachment> implements Repository {

    public final static String DEFAULT_ATTACH_DIRECTORY = "attachments";
    public final static String PARAM_ATTACHMENT_HOME_DIRECTORY = "pleasant.util.REPOSITORY_PATH";

    @EJB
    private org.peasant.util.repositoryimpl.AttachmentFacade ejbFacade;
    @PersistenceContext(unitName = "GridOnMSystem_PU")
    private EntityManager em;

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
        super(DBAttachment.class);
        File homeDir = new File(homePath);
        if (homeDir.exists()) {
            if (!homeDir.isDirectory()) {
                throw new IllegalArgumentException("homePath must be a directory");
            }
        } else if (!homeDir.mkdirs()) {
            throw new RuntimeException("Can't create the Directory specified by homePath argument");
        }
        this.repositoryPath = homePath;
        this.repoDirectory = homeDir;

    }

    @Override
    public Attachment getAttachment(String aId) {
        DBAttachment da = this.find(aId);
        if (null == da) {
            return null;
        }
        DefaultAttachment a = (DefaultAttachment) this.createAttachment();
    //TODO

        return null;
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

            String date = String.format("%1$tY-%1$tm-%1$td", a.getUploadTime());
            String year = String.format("%1$tY", a.getUploadTime());
            String subDir = year + "/" + date;
            File afolder = new File(repoDirectory, subDir);
            if ((!afolder.exists()) && (!afolder.mkdirs())) {
                throw new RuntimeException("Can't create the attachment folder");
            }
            Path af = java.nio.file.Paths.get(repositoryPath, subDir, filename);
            int i = 1;
            while (Files.exists(af)) {
                filename = fName + "(" + i + ")." + fType;
                i++;
                af = java.nio.file.Paths.get(repositoryPath, subDir, filename);
            }
            String relPath = subDir + "/" + filename;
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

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }
    
    protected static class DBFileAttachment implements Attachment{
        Repository repo;
        DBAttachment dba;
        public DBFileAttachment(DBAttachment a,BasicFileRepository repo){
            if(null==a)
                throw new IllegalArgumentException(DBFileAttachment.class.toString()+": this \"Dbattachment a\" argument can't not be null!");
           this.dba = a;
        }

        @Override
        public String getID() {
            return dba.getAttachmentId();
        }

        @Override
        public String getContentType() {
           return this.dba.getContentType();
        }

        @Override
        public String getName() {
            return this.dba.getName();
        }

        @Override
        public String getBelonger() {
           return this.getBelonger();
        }

        @Override
        public Date getUploadTime() {
            return this.getUploadTime();
        }

        @Override
        public void setContentType(String contentType) {
           this.dba.setContentType(contentType);
        }

        @Override
        public void setBelonger(String owner) {
           this.dba.setBelonger(owner);
        }
        @Override
        public void setUploadTime(Date uploadTime) {
           this.dba.setUploadTime(uploadTime);
        }

        @Override
        public void setName(String name) {
           this.dba.setName(name);
        }

        @Override
        public InputStream getInputStream() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void setInputStream(InputStream is) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public int getSize() {
          return this.dba.getSize();//TODO
        }

        @Override
        public void setSize(int size) {
           this.dba.setSize(size);
        }

        @Override
        public void setAttacher(String attacher) {
           this.dba.setAttacher(attacher);
        }
        @Override
        public String getAttacher() {
  return this.dba.getAttacher();
        }
        
    }

}
