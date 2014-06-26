/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eman.asist;

import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.faces.component.UISelectItem;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import org.eman.asist.model.CandidateValue;

/**
 *
 * @author 谢金光
 */
@Named
@Dependent
public class CandidateValuesPopulator {

    CandidateValue cv;

    public CandidateValuesPopulator() {
    }

    public CandidateValuesPopulator(CandidateValue cv) {
        this.cv = cv;
    }

    public void populateValues(ActionEvent actionEvent) {
        UISelectItem ui = (UISelectItem) actionEvent.getComponent();

        cv = (CandidateValue) ui.getValue();
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
}
