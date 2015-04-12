/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eman.web;

import java.io.Serializable;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author 谢金光
 */
@Named
@ViewScoped
public class TestBean2 implements Serializable{

    private UIComponent com;

    public void action(ActionEvent e) {
        com.getValueExpression("value").setValue(FacesContext.getCurrentInstance().getELContext(), "you get it");
    }

    public UIComponent getCom() {
        return com;
    }

    public void setCom(UIComponent com) {
        this.com = com;
    }
}
