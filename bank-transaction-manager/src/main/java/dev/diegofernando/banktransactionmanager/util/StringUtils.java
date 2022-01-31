package dev.diegofernando.banktransactionmanager.util;

public class StringUtils {
    public static Boolean validateNullOrEmpty(String value) {
        return value != null && !value.isEmpty();
    }

    public static Boolean validateNullOrEmptyInList(String... values) {
        for (String val : values) {
            if (!validateNullOrEmpty(val))
                return false;
        }
        return true;
    }
}
