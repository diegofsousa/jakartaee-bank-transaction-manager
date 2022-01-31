package dev.diegofernando.banktransactionmanager.model.enums;

import java.util.regex.Pattern;

public enum TypePixKey {
    EMAIL, DOCUMENT, RANDOM_KEY;

    public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final Pattern PATTERN = Pattern.compile(EMAIL_PATTERN);

    public static TypePixKey getTypePixEnumByValue(String value) {

        if (value == null)
            return TypePixKey.RANDOM_KEY;

        boolean isNumeric = true;

        try {
            Long.parseLong(value);
        } catch (NumberFormatException nfe) {
            isNumeric = false;
        }

        if (isNumeric && value.length() == 11 || value.length() == 14) {
            return TypePixKey.DOCUMENT;
        } else if (PATTERN.matcher(value).matches()){
            return TypePixKey.EMAIL;
        } else

        return TypePixKey.RANDOM_KEY;
    }
}
