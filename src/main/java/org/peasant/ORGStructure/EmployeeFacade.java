/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.ORGStructure;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.peasant.ORGStructure.model.Employee;
import org.peasant.util.GenericAbstractFacade;

/**
 *
 * @author Administrator
 */
@Stateless
public class EmployeeFacade extends GenericAbstractFacade<Employee> {
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
