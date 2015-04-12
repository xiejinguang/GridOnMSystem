/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.jpa;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.inject.Qualifier;
import org.peasant.util.Converter;

/**
 *在CDI环境下，对{@link Converter}进行标注，使用声明的Converter可被{@link GlobalConverters}按类型或名字进行查找。
 * @author 谢金光
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
public @interface EntityConverter {

    /**
     *建议使用目标转换类型的名字作为该字段值
     * @return
     */
    String value() default "";

    /**
     *建议使用目标转换类型的{@link java.lang.Class}作为该字段值
     * @return
     */
    Class forClass() default Object.class;
}
