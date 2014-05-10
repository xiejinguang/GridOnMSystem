/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.util.web;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.peasant.util.Repository;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author 谢金光
 */

@ManagedBean
@ApplicationScoped
public class FileUploadController implements Serializable{

    @EJB
    Repository repo;

    public String[] getFiles() {
        return new java.io.File("C:\\Program Files\\glassfish-4.0\\glassfish\\domains\\domain1\\config\\attachments\\2014\\2014-05-09").list();
    }

    public void handleFileUpload(FileUploadEvent fue) {
        UploadedFile uf = fue.getFile();
        String filename = uf.getFileName();
        try {
            repo.storeFromStream(uf.getInputstream(), filename, uf.getContentType(), uf.getClass().toString(), "金光", null);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successful", filename + " is uploaded!"));
        } catch (IOException ex) {
            Logger.getLogger(FileUploadController.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed","The file:" + filename + " is failed for uploading! Exception: " + ex.toString()));
        }
    }
}
