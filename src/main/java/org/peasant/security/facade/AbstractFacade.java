/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.peasant.security.facade;

import java.util.List;
import javax.persistence.EntityManager;
import org.peasant.util.GenericAbstractFacade;

/**
 *
 * @author 谢金光
 * @param <T>
 */
public abstract class AbstractFacade<T> extends GenericAbstractFacade<T>{

    public AbstractFacade(Class<T> entityClass) {
        super(entityClass);
    }
    
    
}
