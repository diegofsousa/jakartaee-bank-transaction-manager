package dev.diegofernando.banktransactionmanager.model.enums;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public enum Agency {
    AG_CENTRO("1001", "AGÊNCIA CENTRO"),
    AG_AVENIDA_PRINCIPAL("1002", "AGÊNCIA AVENIDA PRINCIPAL"),
    AG_METRO("1003", "AGÊNCIA METRÔ");

    private String number;
    private String description;

    Agency(String number, String description) {
        this.number = number;
        this.description = description;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static Optional<Agency> getAgencyEnumByNumber(String value) {

        for (Agency number : values()) {
            if (number.getNumber().equals(value)) {
                return Optional.of(number);
            }
        }
        return Optional.empty();
    }

    public static  String listOptionsToString(){
        return Arrays.stream(values()).map(Agency::getNumber).collect(Collectors.joining(", "));
    }
}


