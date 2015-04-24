/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.util;

/**
 *
 * @param <T>
 * @see Converter
 * @author 谢金光
 */
public interface Converter<T> {

    /**
     *
     * @param key the value of key
     * @return the T
     */
    public T getAsObject(String key);

    /**
     *
     * @param value the value of value
     * @return 
     */
    public String getAsString(T value);

    /**
     *
     * @param value the value of value
     * @return the T
     */
    public T convert(Object value);
}
