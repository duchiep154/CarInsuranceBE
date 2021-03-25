package com.c0920g1.c0920g1carinsurancebe.validation;


import com.c0920g1.c0920g1carinsurancebe.utils.regex.RegexToBoolean;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckAge18Validator implements ConstraintValidator<CheckAge18, String> {

   public boolean isValid(String value, ConstraintValidatorContext context) {

      return RegexToBoolean.isCheckDateUp18Age(value);
   }
}
