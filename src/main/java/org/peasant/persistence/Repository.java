/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.persistence;

import java.util.List;
import java.util.Map;

/**
 *
 * @author 谢金光
 */
public interface Repository<T> {

    int count();

    T newInstance();

    void create(T entity);

    void save(T entity);

    T find(Object id);

    List<T> findAll();

    List<T> findByConditions(Map<String, Object> params);

    List<T> findRange(int[] range);

    void remove(T entity);

}
