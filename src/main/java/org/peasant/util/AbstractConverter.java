/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.util;

/**
 *
 * @author 谢金光
 * @param <T>
 */
public abstract class AbstractConverter<T> implements Converter<T> {

    @Override
    public T convert(Object value) {

        if (value instanceof String) {
            return getAsObject((String) value);
        }

        return (T) value;

    }
}
