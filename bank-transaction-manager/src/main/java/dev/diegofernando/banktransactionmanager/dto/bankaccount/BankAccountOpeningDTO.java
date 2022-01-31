package dev.diegofernando.banktransactionmanager.dto.bankaccount;

import dev.diegofernando.banktransactionmanager.dto.customvalidations.AgencyValid;

import javax.validation.constraints.NotBlank;

public class BankAccountOpeningDTO {

    @NotBlank
    private String clientExternalId;

    @AgencyValid
    private String agency;

    public String getClientExternalId() {
        return clientExternalId;
    }

    public void setClientExternalId(String clientExternalId) {
        this.clientExternalId = clientExternalId;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    @Override
    public String toString() {
        return "BankAccountOpeningDTO{" +
                "clientExternalId='" + clientExternalId + '\'' +
                ", agency='" + agency + '\'' +
                '}';
    }
}
