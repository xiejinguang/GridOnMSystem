/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.jpa;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Id;
import org.peasant.util.ReflectUtil;

/**
 *
 * @author 谢金光
 */
public class EntityUtil {

    /**
     *
     * @param entity
     * @return
     * @throws NullPointerException,如果entity是个null.
     */
    public static Object getPrimaryKey(Object entity) {
        Collection<Field> fs = ReflectUtil.getAllFields(entity.getClass());
        for (Field f : fs) {
            if (null != f.getAnnotation(Id.class)) {
                try {
                    return ReflectUtil.getProperty(entity, f.getName());
                } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException ex) {
                    Logger.getLogger(EntityUtil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }
}
