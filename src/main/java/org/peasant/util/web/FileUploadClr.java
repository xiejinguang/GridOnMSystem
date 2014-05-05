/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.util.web;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author 谢金光
 */
public class FileUploadClr {

    public FileUploadClr() {
    }

    public void handleFileUpload(FileUploadEvent fue) {
        UploadedFile uf = fue.getFile();
        String filename = uf.getFileName();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successful", "The file:" + filename + " is uploaded!"));
    }

}
