/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.util.web;

import java.io.Serializable;
import java.util.List;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author 谢金光
 */
@Named(value = "attachmentBean")
@ViewScoped
public class AttachmentBean implements Serializable{

    /**
     * Creates a new instance of AttachmentBean
     */
    public AttachmentBean() {
    }

    public List getAttachments(String owner) {
        return null;//TODO
    }
}
