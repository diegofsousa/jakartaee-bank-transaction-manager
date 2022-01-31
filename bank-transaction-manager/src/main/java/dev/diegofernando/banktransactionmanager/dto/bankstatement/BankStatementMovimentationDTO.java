package dev.diegofernando.banktransactionmanager.dto.bankstatement;

import dev.diegofernando.banktransactionmanager.model.enums.TypeChannelTransaction;
import dev.diegofernando.banktransactionmanager.model.enums.TypeMovementTransaction;

import java.math.BigDecimal;
import java.util.Date;

public class BankStatementMovimentationDTO {
    private String externalId;
    private Date createdAt;
    private BigDecimal value;
    private TypeChannelTransaction typeChannelTransaction;
    private TypeMovementTransaction typeMovementTransaction;

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public TypeChannelTransaction getTypeChannelTransaction() {
        return typeChannelTransaction;
    }

    public void setTypeChannelTransaction(TypeChannelTransaction typeChannelTransaction) {
        this.typeChannelTransaction = typeChannelTransaction;
    }

    public TypeMovementTransaction getTypeMovementTransaction() {
        return typeMovementTransaction;
    }

    public void setTypeMovementTransaction(TypeMovementTransaction typeMovementTransaction) {
        this.typeMovementTransaction = typeMovementTransaction;
    }
}
