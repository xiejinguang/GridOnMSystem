/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author 谢金光
 */
public class ConvertUtil {

   

    /**
     * 目前该函数仅能将value转换为java的基本数据类型
     *
     * @param <T>
     * @param value
     * @param targetClazz
     * @return
     */
    public static <T> Object convertToPrimitive(Object value, Class<T> targetClazz) {
        if (null == value) {
            return null;
        }

        if ((Boolean.TYPE == targetClazz) || (Boolean.class == targetClazz)) {
            if (Boolean.TYPE.isInstance(value)) {
                return value;
            }

            return Boolean.valueOf(value.toString());
        }

        if ((Byte.TYPE == targetClazz) || (Byte.class == targetClazz)) {
            if (Byte.TYPE.isInstance(value)) {
                return value;
            }
            return Byte.valueOf(value.toString());
        }

        if ((Short.TYPE == targetClazz) || (Short.class == targetClazz)) {
            if (Short.TYPE.isInstance(value)) {
                return value;
            }
            return Short.valueOf(value.toString());
        }

        if ((Integer.TYPE == targetClazz) || (Integer.class == targetClazz)) {
            if (Integer.TYPE.isInstance(value)) {
                return value;
            }
            return Integer.valueOf(value.toString());
        }
        if ((Long.TYPE == targetClazz) || (Long.class == targetClazz)) {
            if (Long.TYPE.isInstance(value)) {
                return value;
            }
            return Long.valueOf(value.toString());
        }
        if ((Float.TYPE == targetClazz) || (Float.class == targetClazz)) {
            if (Float.TYPE.isInstance(value)) {
                return value;
            }
            return Float.valueOf(value.toString());
        }

        if ((Double.TYPE == targetClazz) || (Double.class == targetClazz)) {
            if (Double.TYPE.isInstance(value)) {
                return value;
            }
            return Double.valueOf(value.toString());
        }
        if (Character.TYPE == targetClazz) {
            if (Character.TYPE.isInstance(value)) {
                return value;
            }
            if ((value != null) && (value.toString().length() > 0)) {
                return value.toString().charAt(0);
            }
        }

        return Void.TYPE.cast(value);

    }

    /**
     * 如果是子类，可以直接赋值则无须转换，如果targetClazz是Java的原始类型，则调用对应的办法。如果<code>targetClazz</code>是
     * {@link java.util.Date}则使用{@link SimpleDateFormat}("yyyy-MM-dd
     * HH:mm:ss")进行解释。如果最终无法对value进行转换，则原样返回value。你必须处理这种情形。
     *
     * @param <T>
     * @param value
     * @param targetClazz
     * @return
     */
    public static <T> Object convert(Object value, final Class<T> targetClazz) {

        if (null == value) {
            return null;
        }

        if (targetClazz.isAssignableFrom(value.getClass())) {//如果是子类，可以直接赋值则无须转换
            return value;
        }

        if (targetClazz.isPrimitive()) {
            return convertToPrimitive(value, targetClazz);//如果targetClazz是Java的原始类型，则调用对应的办法
        }
        if (Date.class == targetClazz) {
            if (Date.class.isInstance(value)) {
                return value;
            }
            try {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value.toString());
            } catch (ParseException ex) {
                Logger.getLogger(ConvertUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
            return value;
        }
        return value;

    }
}
