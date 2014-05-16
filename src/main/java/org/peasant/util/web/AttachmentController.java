/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.util.web;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.peasant.util.Attachment;
import org.peasant.util.Repository;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author 谢金光
 */
@Named(value = "attachmentController")
@ViewScoped
public class AttachmentController implements Serializable {

    public final static String PARAM_ATTACHMENT_HOME_DIRECTORY = "pleasant.util.web.REPOSITORY_PATH";
    @EJB
    Repository attachRepo;

    private List<Attachment> items;

    /**
     * Get the value of items
     *
     * @return the value of items
     */
    public List<Attachment> getItems() {
        return items;
    }

    /**
     * Set the value of items
     *
     * @param items new value of items
     */
    public void setItems(List<Attachment> items) {
        this.items = items;
    }

    /**
     * Creates a new instance of AttachmentBean
     */
    public AttachmentController() {
    }

    public List<Attachment> getAttachments(String owner) {
        return null;//TODO
    }

    public StreamedContent getStreamContent(Attachment a) throws IOException {
        return new DefaultStreamedContent(a.getInputStream(), a.getContentType(), a.getContentType());
    }
}
