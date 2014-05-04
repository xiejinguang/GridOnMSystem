/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gmsys.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.gmsys.model.entity.RoomSpotInfo;

/**
 *
 * @author 谢金光
 */
@Stateless
public class RoomSpotInfoFacade extends AbstractFacade<RoomSpotInfo> {
    @PersistenceContext(unitName = "GridOnMSystem_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RoomSpotInfoFacade() {
        super(RoomSpotInfo.class);
    }
    
}
