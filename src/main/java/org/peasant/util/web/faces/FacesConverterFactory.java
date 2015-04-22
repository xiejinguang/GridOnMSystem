/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.util.web.faces;

import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 *
 * @author 谢金光
 */
@Named
@Singleton
public class FacesConverterFactory implements Serializable {

    public Converter getConverterByObject(Object object) {
        if (null == object) {
            return null;
        }
        return getConverterByClass(object.getClass());

    }

    public Converter getConverterByID(String id) {
        return FacesContext.getCurrentInstance().getApplication().createConverter(id);
    }

    public Converter getConverterByClass(Class clazz) {
        Converter c = FacesContext.getCurrentInstance().getApplication().createConverter(clazz);
        if (c == null) {
            c = FacesContext.getCurrentInstance().getApplication().createConverter(clazz.getCanonicalName());
        }
        if (c == null) {
            c = FacesContext.getCurrentInstance().getApplication().createConverter(clazz.getSimpleName());
        }
        return c;
    }

    public Converter getConverterByClassName(String classname) throws ClassNotFoundException {
        Class clazz = Class.forName(classname);
        return getConverterByClass(clazz);

    }
}
