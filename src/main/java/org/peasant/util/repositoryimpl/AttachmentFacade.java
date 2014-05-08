/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.peasant.util.repositoryimpl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.gmsys.ejb.AbstractFacade;
import org.peasant.util.repositoryimpl.DBAttachment;

/**
 *
 * @author 谢金光
 */
@Stateless
public class AttachmentFacade extends AbstractFacade<DBAttachment> {
    @PersistenceContext(unitName = "GridOnMSystem_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AttachmentFacade() {
        super(DBAttachment.class);
    }
    
}
