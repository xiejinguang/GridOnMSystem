/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.util.web;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import org.eman.asist.model.AsistCandidateValue;
import org.peasant.util.ReflectUtil;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author 谢金光
 */
public class JsfModelBuilder {

    /**
     *
     * @param <T>
     * @param entityClass the value of entityClass
     * @param items the value of items
     * @param childproperty the value of childproperty
     * @param labelProperty
     * @return
     * @throws java.lang.IllegalAccessException
     */
    public static <T> List<SelectItem> buildSelectItems(Class<T> entityClass, Collection<T> items, String childproperty, String labelProperty) throws IllegalArgumentException, IllegalAccessException,InvocationTargetException{
        List<SelectItem> ls = new ArrayList<>(items.size());
        for (T e : items) {
            buildHierarchicalSelectItem(entityClass, e, childproperty, labelProperty);
        }
        return ls;

    }

    /**
     *为对象创建SelectItem,若对象存在子代对象，则创建{@link javax.faces.model.SelectItemGroup},否则创建{@link javax.faces.model.SelectItem}.
     * @param <T>
     * @param entityClass the value of entityClass
     * @param entity the value of entity
     * @param childProperty the value of childproperty
     * @param labelProperty the value of labelProperty
     * @return the javax.faces.model.SelectItem
     * @throws IllegalArgumentException  
     * @throws java.lang.IllegalAccessException
     * @throws java.lang.reflect.InvocationTargetException
     */
    public static <T> SelectItem buildHierarchicalSelectItem(Class<T> entityClass, T entity, String childProperty, String labelProperty) throws IllegalArgumentException, IllegalAccessException,InvocationTargetException {

        T c = (T) ReflectUtil.getProperty(entity, childProperty);
        String label = (String) ReflectUtil.getProperty(entity, labelProperty);
        if (null != c) {

            if ((c instanceof Collection) && (((Collection) c).isEmpty())) {
                return new SelectItem(entity,label);
            }

            SelectItemGroup sg = new SelectItemGroup(label);
            sg.setValue(entity);
            List<SelectItem> cs = new ArrayList<>();

            if (c instanceof Collection) {
                for (T ce : (Collection<T>) c) {
                    cs.add(buildHierarchicalSelectItem(entityClass, ce, childProperty, labelProperty));
                }
            } else {
                cs.add(buildHierarchicalSelectItem(entityClass, c, childProperty, labelProperty));
            }
            SelectItem[] cis = new SelectItem[cs.size()];
            cis = cs.toArray(cis);
            sg.setSelectItems(cis);
            return sg;

        } else {
            return new SelectItem(entity,label);
        }
    }

    public static TreeNode buildTreeNodeForEntity(AsistCandidateValue entity, TreeNode parent) {
        TreeNode node = new DefaultTreeNode(entity, parent);
        for (AsistCandidateValue child : entity.getAsistCandidateValueCollection()) {
            buildTreeNodeForEntity(child, node);
        }
        return node;
    }

}
