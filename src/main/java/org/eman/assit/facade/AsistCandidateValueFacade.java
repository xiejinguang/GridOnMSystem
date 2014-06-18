/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.eman.assit.facade;

import java.util.Arrays;
import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.eman.assit.model.AsistCandidateValue;
import org.eman.assit.model.CandidateValue;

/**
 *
 * @author 谢金光
 */
@Stateless
public class AsistCandidateValueFacade extends AbstractFacade<AsistCandidateValue> {
    @PersistenceContext(unitName = "GridOnMSystem_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AsistCandidateValueFacade() {
        super(AsistCandidateValue.class);
    }
    public void addChildren ( CandidateValue parent,CandidateValue... children){
        for(CandidateValue child:children){
            parent.getChildren().add(child);
        }
        this.edit((AsistCandidateValue)parent);        

    }
    @Override
    @Transactional
    public void remove(AsistCandidateValue entity){
        super.remove(entity);
        if( entity.getParentID()!=null ){
            entity.getParentID().getAsistCandidateValueCollection().remove(entity);
            this.edit(entity.getParentID());
        }
        
            
        
    }
   
}
