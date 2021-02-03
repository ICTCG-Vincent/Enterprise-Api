package com.ictcg.service.constraint;

import com.ictcg.service.models.Address;

import javax.validation.*;
import java.util.Set;

public class ValidEnterpriseAddressConstraintValidator implements ConstraintValidator<ValidEnterpriseAddressConstraint, Set<Address>> {

    @Override
    public boolean isValid(Set<Address> values, ConstraintValidatorContext context) {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        if(values != null){
            for (Address address : values) {
                Set<ConstraintViolation<Address>> constraintViolations =
                        validator.validate(address);

                if (constraintViolations.size() > 0 ) {
                    for (ConstraintViolation<Address> contraintes : constraintViolations) {
                        context.buildConstraintViolationWithTemplate(contraintes.getRootBeanClass().getSimpleName()+
                                "." + contraintes.getPropertyPath() + " " + contraintes.getMessage()).addConstraintViolation();
                    }
                    return false;
                }
            }
        }

        return true;
    }
}
