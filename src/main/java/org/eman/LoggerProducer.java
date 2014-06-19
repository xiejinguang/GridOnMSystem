/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.eman;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author 谢金光
 */
public class LoggerProducer {

    public LoggerProducer() {
    }
    @Produces @Dependent
    public  Logger getDefaultLogger(){
       return LoggerFactory.getLogger("GlobalLogger");
    }
}
