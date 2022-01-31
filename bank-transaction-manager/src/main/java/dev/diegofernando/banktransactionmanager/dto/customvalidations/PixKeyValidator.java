package dev.diegofernando.banktransactionmanager.dto.customvalidations;

import dev.diegofernando.banktransactionmanager.model.enums.TypePixKey;
import dev.diegofernando.banktransactionmanager.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PixKeyValidator implements ConstraintValidator<PixKeyValid, String> {
    @Override
    public void initialize(PixKeyValid constraintAnnotation) {}

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {

        if ("".equals(value))
            return false;

        return value == null || !TypePixKey.RANDOM_KEY.equals(TypePixKey.getTypePixEnumByValue(value));
    }
}
