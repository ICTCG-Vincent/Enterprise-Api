package com.ictcg.service.constraint;

import com.ictcg.service.models.Contact;
import com.ictcg.service.types.Status;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class ValidStatusConstraintValidator implements ConstraintValidator<ValidStatusConstraint, Contact> {

    @Override
    public boolean isValid(Contact contact, ConstraintValidatorContext context) {
         if(contact != null
                 && Status.Freelance.equals(contact.getStatus())){
            return !StringUtils.isEmpty(contact.getTva());
         }
         return true;
    }
}
