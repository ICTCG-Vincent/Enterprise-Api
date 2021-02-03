package com.ictcg.service.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class ValidTvaConstraintValidator implements ConstraintValidator<ValidTvaConstraint, String> {

    private String vtaFormat = "(BE)?0[0-9]{9}";

    @Override
    public boolean isValid(String tva, ConstraintValidatorContext context) {
         if(tva != null){
             Pattern p = Pattern.compile(vtaFormat);
             return p.matcher(tva).matches();
         }
         return true;
    }
}
