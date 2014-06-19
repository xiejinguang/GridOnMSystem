/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eman;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author 谢金光
 */
public class PersistenceContextProducer {

    @PersistenceContext(unitName = "GridOnMSystem_PU")
    EntityManager em;

    @Produces
    @PersistenceUnit(name = "asist_PU")
    public EntityManager getEntityManager() {
        return em;
    }

    @Produces
    @Asist
    public EntityManager getAsistEntityManager() {
        return em;
    }
}
