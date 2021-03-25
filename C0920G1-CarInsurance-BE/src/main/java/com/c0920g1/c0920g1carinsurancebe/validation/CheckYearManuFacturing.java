package com.c0920g1.c0920g1carinsurancebe.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = CheckYearManuFacturingValidator.class)
@Documented
public @interface CheckYearManuFacturing {
    String message() default "Năm sản xuất phải từ 2000 đến hiện tại";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
