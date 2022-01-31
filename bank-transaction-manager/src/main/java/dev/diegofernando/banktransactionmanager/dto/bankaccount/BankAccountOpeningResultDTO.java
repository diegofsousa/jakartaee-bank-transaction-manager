package dev.diegofernando.banktransactionmanager.dto.bankaccount;

import dev.diegofernando.banktransactionmanager.dto.client.ClientSimpleDTO;
import dev.diegofernando.banktransactionmanager.model.enums.TypeAccount;

import java.math.BigDecimal;
import java.util.Date;

public class BankAccountOpeningResultDTO {
    private String externalId;
    private String number;
    private String agency;
    private Date openingAt;
    private Date updatedAt;
    private TypeAccount typeAccount;
    private Boolean active;
    private BigDecimal balance;
    private ClientSimpleDTO client;

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

    public Date getOpeningAt() {
        return openingAt;
    }

    public void setOpeningAt(Date openingAt) {
        this.openingAt = openingAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public TypeAccount getTypeAccount() {
        return typeAccount;
    }

    public void setTypeAccount(TypeAccount typeAccount) {
        this.typeAccount = typeAccount;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public ClientSimpleDTO getClient() {
        return client;
    }

    public void setClient(ClientSimpleDTO client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "BankAccountOpeningResultDTO{" +
                "externalId='" + externalId + '\'' +
                ", number='" + number + '\'' +
                ", agency='" + agency + '\'' +
                ", openingAt=" + openingAt +
                ", updatedAt=" + updatedAt +
                ", typeAccount=" + typeAccount +
                ", active=" + active +
                ", balance=" + balance +
                ", client=" + client +
                '}';
    }
}
