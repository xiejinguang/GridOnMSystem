/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.util;

import java.lang.reflect.Array;
import java.util.Date;
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

    protected Class<T> entityClass;

    public GenericFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    public List<T> findByConditions(Map<String, Object> params) {

        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<T> root = cq.from(entityClass);
        cq.select(root);
        Expression cons = null;
        Expression subExp = null;
        Expression path = null;
        for (String field : params.keySet()) {

            path = root.get(field);
            Object value = params.get(field);
            if (value instanceof String) {
                if (((String) value).isEmpty()) {
                    continue;
                }
                subExp = cb.like(path, "%" + ((String) value)+ "%");
            } else if (value instanceof java.util.Collection) {
                subExp = path.in((java.util.Collection) value);
            } else if (value.getClass().isArray()) {

                if (Array.getLength(value) < 3) {
                    if (Comparable.class.isAssignableFrom(value.getClass().getComponentType())) {
                        Comparable[] vs = (Comparable[]) value;
                        if (vs[0] != null) {
                            subExp = cb.greaterThanOrEqualTo(path, vs[0]);
                        }
                        if (vs[1] != null) {
                            subExp = (subExp == null) ? cb.lessThan(path, vs[1]) : cb.and(subExp, cb.lessThan(path, vs[1]));
                        }
                        // cb.between((Expression<Comparable>) subExp, vs[0], vs[1]);
                    }
                }

            } else {
                subExp = cb.equal(path, value);
            }

            cons = null == cons ? subExp : cb.and(cons, subExp);

        }
        if (cons != null) {
            cq.where(cons);
        }
        return getEntityManager().createQuery(cq).getResultList();
    }
}
