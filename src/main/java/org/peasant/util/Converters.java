/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.util;

import java.io.Serializable;

/**
 *提供获取特定{@link Converter}的方法。
 * @author 谢金光
 */
public interface Converters extends Serializable{
    
    /**
     *
     * @param <T>
     * @param clazz
     * @return {@link Converter}，或null当找不到特定的类型的Converter时或在查找时发生Exception
     */
    public Converter getConverter(Class clazz);

    /**
     *
     * @param name
     * @return {@link Converter}，或null当找不到特定名字的Converter时或在查找时发生Exception
     */
    public Converter getConverter(String name);

}
