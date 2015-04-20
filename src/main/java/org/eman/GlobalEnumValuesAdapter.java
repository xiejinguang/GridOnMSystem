/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eman;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import org.eman.basic.model.Netnode.Grade;
import org.eman.basic.model.Netnode.NetnodeStatus;

/**
 *
 * @author 谢金光
 */
@Named
@ApplicationScoped
public class GlobalEnumValuesAdapter {

    public Grade[] getNetnodeGrade() {
        return Grade.values();
    }

    public NetnodeStatus[] getNetnodeStatus() {
        return NetnodeStatus.values();
    }
}
