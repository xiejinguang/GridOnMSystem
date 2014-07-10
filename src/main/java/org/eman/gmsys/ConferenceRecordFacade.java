/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.eman.gmsys;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.eman.gmsys.model.ConferenceRecord;

/**
 *
 * @author 谢金光
 */
@Stateless
public class ConferenceRecordFacade extends AbstractFacade<ConferenceRecord> {
    @PersistenceContext(unitName = "GridOnMSystem_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ConferenceRecordFacade() {
        super(ConferenceRecord.class);
    }
    
}
