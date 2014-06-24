/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.eman.asist;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.inject.Qualifier;

/**
 *
 * @author 谢金光
 */
@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD,ElementType.PARAMETER,ElementType.METHOD,ElementType.TYPE})
public @interface Values {
    String key();
}
