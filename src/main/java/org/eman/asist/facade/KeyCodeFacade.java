/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.eman.asist.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.eman.asist.model.KeyCode;

/**
 *
 * @author 谢金光
 */
@Stateless
public class KeyCodeFacade extends AbstractFacade<KeyCode> {
    @PersistenceContext(unitName = "GridOnMSystem_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public KeyCodeFacade() {
        super(KeyCode.class);
    }
    
}
