/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.util;

/**
 *
 * @author 谢金光
 */
public interface Converters {

    public <T> Converter<T> getConverter(Class<T> clazz);

    public Converter getConverter(String name);

}
