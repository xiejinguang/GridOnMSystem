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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.peasant.util.Attachment;
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
        return new DBFileAttachment(da, this);

    }

    @Override
    public List<Attachment> getAllAttachments() {
        List<DBAttachment> dbAs = this.findAll();
        List<Attachment> as = new LinkedList<Attachment>();
        for (DBAttachment da : dbAs) {
            as.add(new DBFileAttachment(da, this));
        }
        return as;
    }

    @Override
    public List<Attachment> getAttachmentsByOwner(String owner) {
        Map params = new HashMap();
        params.put("belonger", owner);
        List<DBAttachment> dbAs = this.findSome(new HashMap());
        List<Attachment> as = new LinkedList<Attachment>();
        for (DBAttachment da : dbAs) {
            as.add(new DBFileAttachment(da, this));
        }
        return as;
    }

    @Override
    public boolean delete(String aId) {
        Attachment a = this.getAttachment(aId);
        if (a == null) {
            return true;
        }
        return this.delete(this.getAttachment(aId));
    }

    @Override
    public boolean delete(Attachment a) {
        if (a instanceof DBFileAttachment) {
            DBFileAttachment da = (DBFileAttachment) a;
            if (da.dba != null) {
                this.remove(da.dba);
            }
            return true;
        }
        return false;

    }

    @Override
    public boolean deleteAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InputStream getInputStream(String aId) throws IOException {
        Attachment a = this.getAttachment(aId);
        if (null == a) {
            return null;
        }
        return a.getInputStream();
    }

    @Override
    public List<InputStream> getInputStreams(String owner) throws IOException {
        List<InputStream> isL = new LinkedList<InputStream>();
        List<Attachment> aL = this.getAttachmentsByOwner(owner);
        for (Attachment a : aL) {

            isL.add(a.getInputStream());
        }
        return isL;
    }

    @Override
    public OutputStream getOutputStream(String aId) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Attachment createAttachment() {
        DBAttachment dba = new DBAttachment(UUID.randomUUID().toString());

        return new DBFileAttachment(dba, this);
    }

    protected InputStream newInputStreamByPath(String relPath) throws IOException {
        if (relPath == null) {
            return null;
        }
        return Files.newInputStream(this.repoDirectory.toPath().resolve(relPath));
    }

    /**
     *
     * @param name the value of name
     * @param owner the value of owner
     * @param contentType the value of contentType
     * @param uploadTime the value of uploadTime
     * @param is the value of InputStream
     */
    @Override
    public Attachment createAttachment(String name, String owner, String contentType, Date uploadTime, InputStream is) {
        DBAttachment dba = new DBAttachment(UUID.randomUUID().toString());
        DBFileAttachment dbfa = new DBFileAttachment(dba, this);
        dbfa.setName(name);
        dbfa.setBelonger(owner);
        dbfa.setContentType(contentType);
        dbfa.setUploadTime(uploadTime);
        dbfa.setInputStream(is);
        return dbfa;
    }

    /**
     *
     * @param inputStream the value of inputStream
     * @param name the value of name
     * @param contentType the value of contentType
     * @param owner the value of owner
     * @param uploadTime the value of uploadTime
     */
    @Override
    public Attachment storeFromStream(InputStream inputStream, String name, String contentType, String owner, Date uploadTime) throws IOException {
        Attachment a = createAttachment(name, owner, contentType, uploadTime, inputStream);
        return store(a);
    }

    @Override
    public Attachment store(Attachment a) throws IOException {

        if (null == getAttachment(a.getID())) {
            InputStream is = a.getInputStream();

            if (null == is) {
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
            int size = (int) Files.copy(is, af, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            is.close();
            if (a instanceof DBFileAttachment) {
                DBAttachment dba = ((DBFileAttachment) a).dba;
                dba.setRelPath(relPath);
                this.create(dba);

            } else {
                DBAttachment dba = new DBAttachment(a.getID());
                dba.setName(a.getName());
                dba.setAttacher(a.getAttacher());
                dba.setBelonger(a.getBelonger());
                dba.setContentType(a.getContentType());
                dba.setSize(size);
                dba.setUploadTime(a.getUploadTime());
                dba.setRelPath(relPath);
                this.create(dba);

            }
            return this.getAttachment(a.getID());

        }
        return null;
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    protected static class DBFileAttachment implements Attachment {

        final BasicFileRepository repo;
        final DBAttachment dba;
        InputStream is;
        boolean isInputStreamSeted = false;

        public DBFileAttachment(DBAttachment a, BasicFileRepository repo) {
            if (null == a) {
                throw new IllegalArgumentException(DBFileAttachment.class.toString() + ": this \"Dbattachment a\" argument can't not be null!");
            }
            this.dba = a;
            this.repo = repo;
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
            return this.dba.getBelonger();
        }

        @Override
        public Date getUploadTime() {
            return this.dba.getUploadTime();
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
        public InputStream getInputStream() throws IOException {
            if (this.isInputStreamSeted) {
                return this.is;
            }
            return repo.newInputStreamByPath(this.dba.getRelPath());
        }

        @Override
        public void setInputStream(InputStream is) {
            this.isInputStreamSeted = true;
            this.is = is;
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
