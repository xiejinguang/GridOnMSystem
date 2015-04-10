/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.model;

import java.lang.annotation.Annotation;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.peasant.util.Converter;
import org.peasant.util.Converters;

/**
 *CDI环境下的Converters,为应用程序提供全局可注入的{@link Conveters}。
 * 
 * @see EntityConverter
 * @author 谢金光
 */
@Singleton
public class GlobalConverters implements Converters {

    @Inject
    @Any
    private Instance<Converter<? extends Object>> anyconverter;

    @Override
    public <T> Converter<T> getConverter(final Class<T> clazz) {
        //尝试使用converter进行转换
        if (anyconverter != null) {
            Instance<Converter<? extends Object>> converters;
            converters = anyconverter.select(new EntityConverter() {

                @Override
                public String value() {
                    return clazz.getName();
                }

                @Override
                public Class forClass() {
                    return clazz;
                }

                @Override
                public Class<? extends Annotation> annotationType() {
                    return EntityConverter.class;
                }
            });
            if (converters != null) {
                Converter<T> c;
                try {
                    return (Converter<T>) converters.get();
                } catch (Exception ex) {
                    
                    Logger.getLogger(GlobalConverters.class.getName()).log(Level.WARNING, null, ex);
                }
                return null;
            }
        }

        return null;
    }
    private static final Logger LOG = Logger.getLogger(GlobalConverters.class.getName());

    @Override
    public Converter getConverter(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
