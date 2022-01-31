package dev.diegofernando.banktransactionmanager.dto;

import java.util.HashMap;

public class ErrorDTO {
    private String key;
    private HashMap<String, String> errorFields;

    public ErrorDTO(String key, HashMap<String, String> errorFields) {
        this.key = key;
        this.errorFields = errorFields;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public HashMap<String, String> getErrorFields() {
        return errorFields;
    }

    public void setErrorFields(HashMap<String, String> errorFields) {
        this.errorFields = errorFields;
    }
}
