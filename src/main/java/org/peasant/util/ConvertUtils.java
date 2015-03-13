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
public class ConvertUtils {
    
    /**
     *目前该函数仅能将value转换为java的基本数据类型
     * @param <T>
     * @param value
     * @param targetClazz
     * @return
     */
    public static <T> Object convertToPrimitive(Object value, Class<T> targetClazz) {
        if (String.class == targetClazz) {
            return String.valueOf(value);
        } else if ((Integer.TYPE == targetClazz)
                || (Integer.class == targetClazz)) {
            return Integer.parseInt(value.toString());
        } else if ((Long.TYPE == targetClazz)
                || (Long.class == targetClazz)) {
            return Long.valueOf(value.toString());
        } else if ((Float.TYPE == targetClazz)
                || (Float.class == targetClazz)) {
            return Float.valueOf(value.toString());
        } else if ((Short.TYPE == targetClazz)
                || (Short.class == targetClazz)) {
            return Short.valueOf(value.toString());
        } else if ((Double.TYPE == targetClazz)
                || (Double.class == targetClazz)) {
            return Double.valueOf(value.toString());
        } else if (Character.TYPE == targetClazz) {
            if ((value != null) && (value.toString().length() > 0)) {
                return value.toString().charAt(0);
            }
        } else if (Date.class == targetClazz) {
            try {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value.toString());
            } catch (ParseException ex) {
                Logger.getLogger(ConvertUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
            return value;

        } else {
            return value;
        }
        return value;
    }
}
