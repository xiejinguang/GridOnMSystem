/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.util.web;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.peasant.util.Attachment;
import org.peasant.util.Repository;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author 谢金光
 */
@Named(value = "attachmentController")
@ViewScoped
public class AttachmentController implements Serializable {

    @Inject
    Repository attachRepo;
    
    String owner ;
    List<Attachment> selecteds;

    public List<Attachment>  getSelecteds() {
        return selecteds;
    }

    public void setSelecteds(List<Attachment> selecteds) {
        this.selecteds = selecteds;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
    
    /**
     * Creates a new instance of AttachmentBean
     */
    public AttachmentController() {
    }

    public List<Attachment> getAllAttachments() {
        return attachRepo.getAllAttachments();
    }

    public List<Attachment> getAttachmentsByOwner(String owner) {
        return attachRepo.getAttachmentsByOwner(owner);
    }
    public List<Attachment> getAttachments(){
        return this.getAttachmentsByOwner(this.owner);
    }

    public StreamedContent getStreamContent(Attachment a) throws IOException {
        ExternalContext ec =  FacesContext.getCurrentInstance().getExternalContext();
        
        //当使用HTTP进行下载时，必须使用URLEncoder对文件名进行编号（若是firefox则必须使用new String(filename.getBytes("UTF-8"),"ISO-8859-1")，否则在下载保存对话框中的中文文件名将是乱码，请参见HTTP Header： Content-Disposition
        return new DefaultStreamedContent(a.getInputStream(), a.getContentType(),HttpUtils.encodeFilename((HttpServletRequest)ec.getRequest(), (HttpServletResponse)ec.getResponse(), a.getName()));
    }

    public void handleFileUpload(FileUploadEvent fue) {
        UploadedFile uf = fue.getFile();
        String filename = uf.getFileName();
        String user = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        try {
            attachRepo.storeFromStream(uf.getInputstream(), filename, uf.getContentType(), this.owner, user, null);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successful", filename + " is uploaded!"));
        } catch (IOException ex) {
            Logger.getLogger(FileUploadController.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed", "The file:" + filename + " is failed for uploading! Exception: " + ex.toString()));
        }
    }
    public void destory(){
        for(Attachment a :this.selecteds){
            attachRepo.delete(a);
        }
    }
}
