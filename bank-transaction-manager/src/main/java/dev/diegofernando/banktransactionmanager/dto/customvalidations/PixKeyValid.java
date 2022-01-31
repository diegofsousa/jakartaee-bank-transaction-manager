package dev.diegofernando.banktransactionmanager.dto.customvalidations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PixKeyValidator.class)
@Documented
public @interface PixKeyValid {

    String message() default "Possible entries for this field are: 11-position string for CPF, 14-position string for CNPJ or valid email address.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String value() default "";
}
