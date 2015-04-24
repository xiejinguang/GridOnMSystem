/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.jpa;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.peasant.util.AbstractConverter;
import org.peasant.util.Converter;
import org.peasant.util.Converters;

/**
 * CDI环境下的Converters,为应用程序提供全局可注入的{@link Conveters}。动态获取应用程序内可用的{@link Converter}
 *
 * @see EntityConverter
 * @author 谢金光
 */
@ApplicationScoped
@Logged
public class GlobalConverters implements Converters,Serializable {
 
    public GlobalConverters() {
    }

    @Inject
    @Any
    private Instance<AbstractConverter<? extends Object>> anyconverter;

    private static final Logger LOG = Logger.getLogger(GlobalConverters.class.getName());

    public Converter getConverter(final Class clazz) {

        return getConverter(clazz, null);
    }

    @Override
    public Converter getConverter(String name) {
        return getConverter(null, name);
    }

    /**
     * 参数clazz与name之间是或的关系,动态获取Converter
     *
     * @param clazz
     * @param name
     * @return
     */
    protected Converter getConverter(Class clazz, String name) {
        if (anyconverter != null) {

            for (AbstractConverter ctr : anyconverter) {
                EntityConverter ec = ctr.getClass().getAnnotation(EntityConverter.class);
                if (ec != null) {
                    if (clazz != null && ec.forClass().equals(clazz)) {
                        return ctr;
                    }

                    if (name != null && name.equals(ec.value())) {
                        return ctr;
                    }
                }
            }
        }

        return null;
    }

}
