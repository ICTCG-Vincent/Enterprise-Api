package com.ictcg.service.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = ValidEnterpriseAddressConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEnterpriseAddressConstraint {
    String message() default "";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
