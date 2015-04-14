/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.excel2entity;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.persistence.Embeddable;
import javax.persistence.Entity;

/**
 *标注该属性为复合列，属性的值由多个Excel的列值组合而成，对应{@link Embeddable}
 * ,{@link Entity}
 * @author 谢金光
 */
@Documented
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CompositeColumn {
    
}
