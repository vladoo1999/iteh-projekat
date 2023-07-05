package com.example.fon_classroommanagment.Anotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target( { ElementType.TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = {CheckValuesValidatorAppointmentDTO.class,CheckValuesValidatorFilterDTO.class})
public @interface CheckValues {

    String message() default "Krajnje vreme mora biti vece od pocetnog vremena";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}