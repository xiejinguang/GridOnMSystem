/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gmsys.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.gmsys.model.entity.Grid;

/**
 *
 * @author 谢金光
 */
@Stateless
public class GridFacade extends AbstractFacade<Grid> {
    @PersistenceContext(unitName = "org.legend_GridOnMSystem_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GridFacade() {
        super(Grid.class);
    }
    
}
