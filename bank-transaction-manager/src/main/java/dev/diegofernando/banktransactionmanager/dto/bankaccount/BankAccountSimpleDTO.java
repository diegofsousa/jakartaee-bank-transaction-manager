package dev.diegofernando.banktransactionmanager.dto.bankaccount;

import dev.diegofernando.banktransactionmanager.model.enums.TypeAccount;

public class BankAccountSimpleDTO {
    private String externalId;
    private String number;
    private String agency;
    private TypeAccount typeAccount;

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public TypeAccount getTypeAccount() {
        return typeAccount;
    }

    public void setTypeAccount(TypeAccount typeAccount) {
        this.typeAccount = typeAccount;
    }
}
