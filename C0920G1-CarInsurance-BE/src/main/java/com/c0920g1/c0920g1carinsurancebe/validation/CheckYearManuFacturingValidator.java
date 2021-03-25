package com.c0920g1.c0920g1carinsurancebe.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class CheckYearManuFacturingValidator implements ConstraintValidator<CheckYearManuFacturing, String> {
   public void initialize(CheckYearManuFacturing constraint) {
   }

   public boolean isValid(String value, ConstraintValidatorContext context) {
      try {
         if(!value.equals("")){
            try {
               if(Integer.parseInt(value) >= 2000 && Integer.parseInt(value) <= LocalDate.now().getYear()){
                  return true;
               }
            } catch (NumberFormatException e){
               return false;
            }
         }
         return false;
      }catch (NullPointerException e){
         return false;
      }
   }
}
