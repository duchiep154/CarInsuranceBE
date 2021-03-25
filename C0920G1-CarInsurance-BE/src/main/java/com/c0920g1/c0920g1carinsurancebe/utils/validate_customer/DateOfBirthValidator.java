package com.c0920g1.c0920g1carinsurancebe.utils.validate_customer;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateOfBirthValidator implements ConstraintValidator<DateOfBirth, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (!s.equals("")) {
            int yearOfBirth = Integer.parseInt(s.substring(0,4));
            String currentDate = java.time.LocalDate.now().toString();
            int currentYear = Integer.parseInt(currentDate.substring(0,4));
            int age = currentYear - yearOfBirth;
            return age >= 18 && age <= 150;
        }
        return false;
    }
}
