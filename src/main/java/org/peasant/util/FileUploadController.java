/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.peasant.util;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.FaceletContext;
import javax.inject.Named;
import java.util.logging.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;


/**
 *
 * @author 谢金光
 */
@Named
@ApplicationScoped
public class FileUploadController {



    /**
     * Creates a new instance of FileUploadController
     */
    public FileUploadController() {
    }
    
    public void handleFileUpload(FileUploadEvent fue){
      UploadedFile uf=  fue.getFile();
    }
}
