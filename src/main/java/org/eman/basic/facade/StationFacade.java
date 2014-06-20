/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.eman.basic.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.eman.basic.model.Station;

/**
 *
 * @author 谢金光
 */
@Stateless
public class StationFacade extends AbstractFacade<Station> {
    @PersistenceContext(unitName = "GridOnMSystem_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StationFacade() {
        super(Station.class);
    }
    
}
