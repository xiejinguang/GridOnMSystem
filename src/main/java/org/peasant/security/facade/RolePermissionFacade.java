/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.peasant.security.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.peasant.security.model.RolePermission;

/**
 *
 * @author 谢金光
 */
@Stateless
public class RolePermissionFacade extends AbstractFacade<RolePermission> {
    @PersistenceContext(unitName = "GridOnMSystem_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RolePermissionFacade() {
        super(RolePermission.class);
    }
    
}
