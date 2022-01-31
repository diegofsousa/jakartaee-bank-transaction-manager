package dev.diegofernando.banktransactionmanager.dto.pixkeyregistration;

import dev.diegofernando.banktransactionmanager.dto.customvalidations.PixKeyValid;

import javax.validation.constraints.NotBlank;

public class PixKeyRegistrationSimpleDTO {

    @PixKeyValid
    private String key;

    @NotBlank
    private String accountExternalId;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAccountExternalId() {
        return accountExternalId;
    }

    public void setAccountExternalId(String accountExternalId) {
        this.accountExternalId = accountExternalId;
    }

    @Override
    public String toString() {
        return "PixKeyRegistrationSimpleDTO{" +
                "key='" + key + '\'' +
                ", accountExternalId='" + accountExternalId + '\'' +
                '}';
    }
}
