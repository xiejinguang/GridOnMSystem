/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.eman.basic.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.peasant.util.GenericFacade;
import org.peasant.util.repositoryimpl.DBAttachment;

/**
 *
 * @author 谢金光
 */
@Stateless
public class DBAttachmentFacade extends GenericFacade<DBAttachment> {
    @PersistenceContext(unitName = "GridOnMSystem_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DBAttachmentFacade() {
        super(DBAttachment.class);
    }
    
}