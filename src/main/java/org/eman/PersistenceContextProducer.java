/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eman;

import java.io.Serializable;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.peasant.validation.ValidationSource;

/**
 *
 * @author 谢金光
 */

public class PersistenceContextProducer implements Serializable {

    @PersistenceContext(unitName = "GridOnMSystem_PU")
    EntityManager em;

    @Produces
    @Module(name = "asist")
    @ValidationSource
    public EntityManager getAsistEntityManager() {
        return em;
    }

    @Produces 
    public EntityManager getDefaultEntityManager() {
        return em;
    }
}
