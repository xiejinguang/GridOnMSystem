/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.util.web;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author 谢金光
 */
@Named("dialogBean")
@SessionScoped
public class DialogBean implements Serializable {

    Map<Class, String> viewMap;

    /**
     * Creates a new instance of DialogBean
     */
    public DialogBean() {

    }

    @PostConstruct
    public void init() {
        viewMap = new HashMap<Class, String>();

    }

    public void attachments() {
        RequestContext.getCurrentInstance().openDialog(null, null, null);
    }

    public void closeDialog() {
        RequestContext.getCurrentInstance().closeDialog(null);
    }

    public void selectData(Object data) {
        RequestContext.getCurrentInstance().closeDialog(data);
    }

    public void showDialog(String outcome,Map<String,List<String>> params) {
        Map<String, Object> options = new HashMap<String, Object>();
        //  RequestContext.getCurrentInstance().openDialog(outcome);
        options.put("modal", true);
        options.put("draggable", true);
        options.put("resizable", true);
        options.put("height", 450);
        options.put("width", 650);
        options.put("contentMinWidth ", 600);
        options.put("contentMinHeight", 400);

        RequestContext.getCurrentInstance().openDialog(outcome, options,params);
    }

    public void onChosen(SelectEvent selectEvent) {

    }

    private <T> String getOutcomeFor(T entityClass) {
        return this.viewMap.get(this);
    }
}
