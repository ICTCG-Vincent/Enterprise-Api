package com.ictcg.service.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = ValidTvaConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTvaConstraint {
    String message() default "The format of the vta is not valid, the vta should match (BE)?0[0-9]{9}. Example : BE0723600303";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
