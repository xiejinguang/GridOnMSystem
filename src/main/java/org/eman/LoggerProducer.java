/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.eman;

import java.io.Serializable;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author 谢金光
 */
@Singleton
public class LoggerProducer implements Serializable{

    public LoggerProducer() {
    }
    @Produces @Default
    public  Logger getDefaultLogger(){
       return LoggerFactory.getLogger("GlobalDefaultLogger");
    }
    @Produces @Module(name="asist")
      public  Logger getAsistLogger(){
       return LoggerFactory.getLogger("AsistLogger");
    }
}
