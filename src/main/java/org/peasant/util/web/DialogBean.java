/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.peasant.util.web;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

/**
 *
 * @author 谢金光
 */
@Named("dialogBean")
@SessionScoped
public class DialogBean  implements Serializable{
    /**
     * Creates a new instance of DialogBean
     */
    public DialogBean() {
        
    }
    
    public void attachments(){
        RequestContext.getCurrentInstance().openDialog(null, null, null);
    }
    
}
