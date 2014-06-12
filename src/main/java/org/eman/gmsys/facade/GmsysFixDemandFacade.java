/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.eman.gmsys.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.eman.gmsys.model.GmsysFixDemand;

/**
 *
 * @author 谢金光
 */
@Stateless
public class GmsysFixDemandFacade extends AbstractFacade<GmsysFixDemand> {
    @PersistenceContext(unitName = "GridOnMSystem_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GmsysFixDemandFacade() {
        super(GmsysFixDemand.class);
    }
    
}
