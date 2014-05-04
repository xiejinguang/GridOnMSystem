/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.util;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

/**
 *
 * @author 谢金光
 */
public abstract class GenericFacade<T> {

    private Class<T> entityClass;

    public GenericFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public List<T> findSome(Map<String, Object> params) {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        Root<T> root = cq.from(entityClass);
        Expression cons = null;
        Expression subExp = null;

        for (String field : params.keySet()) {

            subExp = root.get(field);
            Object value = params.get(field);
            if (value instanceof String) {
                subExp = cb.like(subExp, (String) params.get(field));
            } else if (value instanceof java.util.Collection) {
                subExp = subExp.in((java.util.Collection) value);
            } else if (value instanceof java.util.Date) {
                subExp = null;//todo
            } else {
                subExp = cb.equal(subExp, value);
            }

            cons = null == cons ? subExp : cb.and(cons, subExp);

        }
        return getEntityManager().createQuery(cq).getResultList();
    }
}
