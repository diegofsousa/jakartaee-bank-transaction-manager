package dev.diegofernando.banktransactionmanager.dto.customvalidations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AgencyValidator.class)
@Documented
public @interface AgencyValid {

    String message() default "Select a valid bank branch from: '{agencies}'";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String value() default "";
}
