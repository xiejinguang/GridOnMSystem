/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.eman.basic.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.eman.basic.model.BasicNetnode;

/**
 *
 * @author 谢金光
 */
@Stateless
public class BasicNetnodeFacade extends AbstractFacade<BasicNetnode> {
    @PersistenceContext(unitName = "GridOnMSystem_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BasicNetnodeFacade() {
        super(BasicNetnode.class);
    }
    
}
