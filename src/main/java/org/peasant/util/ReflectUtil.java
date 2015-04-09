/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
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

    public static Object getProperty(Object o, String property) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        try {
            Field f = o.getClass().getField(property);
            return f.get(o);
        } catch (NoSuchFieldException ex) {
            String msg = o.getClass().getName() + "不存在字段‘" + property + "',尝试getProperty方法";

            Logger.getLogger(ReflectUtil.class.getName()).log(Level.INFO, msg);
            String pms = getPropertyGetter(property);
            try {
                Method pm = o.getClass().getMethod(pms);
                return pm.invoke(o);
            } catch (NoSuchMethodException ex1) {
                msg = o.getClass().getName() + "不存在字段‘" + property + "',也不存在方法‘" + pms + "'!!!";
                Logger.getLogger(ReflectUtil.class.getName()).log(Level.SEVERE, msg, ex1);
                throw new IllegalArgumentException(msg, ex1);
            }
        }
    }

    /**
     *
     *
     * @throws java.lang.IllegalAccessException
     * @throws java.lang.reflect.InvocationTargetException
     * @MethodName : setFieldValueByName
     * @Description : 根据字段名给对象的字段赋值
     * @param name 字段名
     * @param value 字段值
     * @param target 对象
     *
     */
    public static void setPropertyByName(String name, Object value, Object target) throws IllegalAccessException, InvocationTargetException {

        Class<?> fieldType;
        Field field = getFieldByName(name, target.getClass());
        if (field != null) {
            field.setAccessible(true);
            //获取字段类型 
            fieldType = field.getType();
            if (fieldType.isInstance(value)) {
                field.set(target, value);
            } else {
                field.set(target, ConvertUtil.convert(value, fieldType));
            }
        } else {
            Method sm = getSetMethodByFieldName(name, target.getClass());

            if (null != sm) {
                fieldType = sm.getParameterTypes()[0];

                if (fieldType.isInstance(value)) {
                    sm.invoke(target, value);
                } else {
                    sm.invoke(target, ConvertUtil.convert(value, fieldType));
                }
            } else {
                throw new IllegalArgumentException(target.getClass().getSimpleName() + "类不存在属性\" " + name + "\"!!!");
            }
        }

    }

    public static Class getPropertyType(String name, Object target) {

        Field field = getFieldByName(name, target.getClass());
        if (field != null) {
            field.setAccessible(true);
            //获取字段类型 
            return field.getType();

        } else {
            Method sm = getSetMethodByFieldName(name, target.getClass());

            if (null != sm) {
                return sm.getParameterTypes()[0];
            } else {
                throw new IllegalArgumentException(target.getClass().getSimpleName() + "类不存在属性\" " + name + "\"!!!");
            }
        }

    }

}
