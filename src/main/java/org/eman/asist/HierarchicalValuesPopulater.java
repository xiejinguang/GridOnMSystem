/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eman.asist;

import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.component.UISelectItem;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import org.eman.asist.model.CandidateValue;

/**
 *
 * @author 谢金光
 */
@Named
@Dependent
public class HierarchicalValuesPopulater {

    private HierarchicalValuesPopulater superior;
    private HierarchicalValuesPopulater subordinate;

    CandidateValue cv;

    public HierarchicalValuesPopulater(HierarchicalValuesPopulater superior, HierarchicalValuesPopulater subordinate, CandidateValue cv) {
        this.superior = superior;
        this.subordinate = subordinate;
        this.cv = cv;
    }

    public HierarchicalValuesPopulater() {
    }

    public void populateValues(ActionEvent actionEvent) {
        UISelectItem ui = (UISelectItem) actionEvent.getComponent();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("you know what I want"));

        cv = (CandidateValue) ui.getValue();
    }

    public void popValues(ValueChangeEvent vce) {

        Object newValue = vce.getNewValue();

        CandidateValue nv = null;
        nv = findSubordinate(newValue, nv);
        if (subordinate != null) {
            subordinate.setCv(nv);
        }

    }

    protected CandidateValue findSubordinate(Object newValue, CandidateValue nv) {
        if (newValue == null) {
            return null;
        }
        for (CandidateValue child : cv.getChildren()) {
            if (newValue.equals(child.getValue())) {
                nv = child;
            }

        }
        return nv;
    }

    public Collection<CandidateValue> getValues() {
        if (cv == null) {
            return null;
        }
        return cv.getChildren();
    }

    public CandidateValue getCv() {
        return cv;
    }

    public void setCv(CandidateValue cv) {
        this.cv = cv;
    }

    public HierarchicalValuesPopulater getSuperior() {
        return superior;
    }

    public void setSuperior(HierarchicalValuesPopulater superior) {
        this.superior = superior;
    }

    public HierarchicalValuesPopulater getSubordinate() {
        return subordinate;
    }

    public void setSubordinate(HierarchicalValuesPopulater subordinate) {
        this.subordinate = subordinate;
    }
}
