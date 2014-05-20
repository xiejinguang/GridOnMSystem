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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
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
    Attachment selected;

    public Attachment getSelected() {
        return selected;
    }

    public void setSelected(Attachment selected) {
        this.selected = selected;
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

    public List<Attachment> getAttachments(String owner) {
        return attachRepo.getAttachmentsByOwner(owner);
    }

    public StreamedContent getStreamContent(Attachment a) throws IOException {
        return new DefaultStreamedContent(a.getInputStream(), a.getContentType(), a.getContentType());
    }

    public void handleFileUpload(FileUploadEvent fue) {
        UploadedFile uf = fue.getFile();
        String filename = uf.getFileName();
        String user = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        try {
            attachRepo.storeFromStream(uf.getInputstream(), filename, uf.getContentType(), uf.getClass().toString(), user, null);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successful", filename + " is uploaded!"));
        } catch (IOException ex) {
            Logger.getLogger(FileUploadController.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed", "The file:" + filename + " is failed for uploading! Exception: " + ex.toString()));
        }
    }
}
