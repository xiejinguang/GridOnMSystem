/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.ORGStructure.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.peasant.ORGStructure.model.Employee;
import org.peasant.util.GenericFacade;

/**
 *
 * @author 谢金光
 */
@Stateless
public class EmployeeFacade extends GenericFacade<Employee> {
    @PersistenceContext(unitName = "GridOnMSystem_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmployeeFacade() {
        super(Employee.class);
    }
    
}
