/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author 谢金光
 */
public class ReflectUtil {

    public static String getPropertyGetter(String property) {
        String pmg = "get" + Character.toUpperCase(property.charAt(0)) + property.substring(1);
        return pmg;
    }
    public static String getPropertySetter(String property) {
        String pms = "set" + Character.toUpperCase(property.charAt(0)) + property.substring(1);
        return pms;
    }
    /**
     * 根据字段名获取该字段的set方法，若字段名为"fieldName",则猜测set方法名为"setFieldName";
     *
     *
     * @param fieldName
     * @param entityClazz
     * @return 如果未找到匹配的set方法则返回null;
     */
    public static Method getSetMethodByFieldName(String fieldName, Class<?> entityClazz) {
        fieldName = fieldName.trim();
        String smn = "set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
        Method[] ms = entityClazz.getMethods();
        for (Method m : ms) {
            if (m.getName().equals(smn)) {
                return m;
            }
        }
        return null;
    }

    /**
     * @MethodName : getFieldByName
     * @Description : 根据字段名获取字段
     * @param fieldName 字段名
     * @param clazz 包含该字段的类
     * @return 字段
     */
   public static Field getFieldByName(String fieldName, Class<?> clazz) {
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }
        return null;
    }
   public static Object getProperty(Object o , String property)throws IllegalArgumentException,IllegalAccessException,InvocationTargetException{
       try{
        Field f = o.getClass().getField(property);
        return f.get(o);
       }
       catch(NoSuchFieldException ex){
           String msg = o.getClass().getName() + "不存在字段‘" + property + "',尝试getProperty方法";

            Logger.getLogger(JsfModelBuilder.class.getName()).log(Level.INFO, msg, ex);
            String pms = getPropertyGetter(property);
            try {
                Method pm = o.getClass().getMethod(pms);
              return  pm.invoke(o);
            } catch (NoSuchMethodException ex1){
               msg = o.getClass().getName() + "不存在字段‘" + property + "',也不存在方法‘" + pms + "'!!!";
               Logger.getLogger(JsfModelBuilder.class.getName()).log(Level.SEVERE, msg, ex1);
                throw new IllegalArgumentException(msg, ex1);
            }
       }
   }
    
}
