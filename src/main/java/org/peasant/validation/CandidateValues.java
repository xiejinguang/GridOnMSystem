/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.ConstraintTarget;
import javax.validation.Payload;

/**
 *
 * @author 谢金光
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
@Constraint(validatedBy = CandidateValuesValidator.class)
public @interface CandidateValues {

    String key();
    String message() default "{org.peasant.validation.CandidateValues.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    
    
//Custom constraints that can be applied to both return values and method parameters
//require a validationAppliesTo element to identify the target of the constraint.
    //ConstraintTarget validationAppliesTo() default ConstraintTarget.IMPLICIT;
}
