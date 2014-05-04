/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.util.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.FaceletContext;
import javax.inject.Named;
import org.peasant.util.Repository;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author 谢金光
 */
@Named
@RequestScoped
public class FileUploadController {

    @EJB
    Repository repo;

    public String[] getFiles() {
        return new java.io.File("C:\\Program Files\\glassfish-4.0\\glassfish\\domains\\domain1\\config\\attachments\\2014\\2014-05-03").list();
    }

    /**
     * Creates a new instance of FileUploadController
     */
    public FileUploadController() {
    }

    public void handleFileUpload(FileUploadEvent fue) {
        UploadedFile uf = fue.getFile();
        String filename = uf.getFileName();
        try {
            repo.storeFromStream(uf.getInputstream(), filename, uf.getContentType(), uf.getClass().toString(), null);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successful","The file:" + filename + " is uploaded!"));
        } catch (IOException ex) {
            Logger.getLogger(FileUploadController.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed","The file:" + filename + " is failed for uploading! Exception: " + ex.toString()));
        }
    }
}
