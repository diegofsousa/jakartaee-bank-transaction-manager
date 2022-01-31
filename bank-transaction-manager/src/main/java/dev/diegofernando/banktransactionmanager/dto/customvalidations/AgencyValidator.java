    package dev.diegofernando.banktransactionmanager.dto.customvalidations;


import dev.diegofernando.banktransactionmanager.model.enums.Agency;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class AgencyValidator implements ConstraintValidator<AgencyValid, String> {

    @Override
    public void initialize(AgencyValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null){
            return false;
        }

        if (!Agency.getAgencyEnumByNumber(value).isPresent()){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate( "Select a valid bank agency from: " + Agency.listOptionsToString()).addConstraintViolation();
            return false;
        }

        return true;
    }
}
