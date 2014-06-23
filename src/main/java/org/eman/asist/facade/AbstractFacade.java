/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.eman.asist.facade;

import java.util.List;
import javax.persistence.EntityManager;
import org.peasant.util.GenericFacade;

/**
 *
 * @author 谢金光
 */
public abstract class AbstractFacade<T> extends GenericFacade<T>{


    public AbstractFacade(Class<T> entityClass) {
        super(entityClass);
    }   
}
