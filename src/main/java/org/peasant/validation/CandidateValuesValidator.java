/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.validation;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;

/**
 *
 * @author 谢金光
 */
@SupportedValidationTarget(ValidationTarget.ANNOTATED_ELEMENT)
public class CandidateValuesValidator implements ConstraintValidator<CandidateValues, String> {

    @Inject
    @ValidationSource
    EntityManager em;

    public CandidateValuesValidator() {
    }

    @Override
    public void initialize(CandidateValues constraintAnnotation) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
