package com.ictcg.service.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = ValidStatusConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidStatusConstraint {
    String message() default "A vta number should be included if the status of the contact is freelance";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
