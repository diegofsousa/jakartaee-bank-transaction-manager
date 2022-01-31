package dev.diegofernando.banktransactionmanager.dto.financialmovement;

import java.io.Serializable;
import java.math.BigDecimal;

public class FinancialMovementDTO implements Serializable {
    private String originExternalId;
    private String destinationExternalId;
    private BigDecimal value;
    private String statementExternalId;

    public FinancialMovementDTO(String originExternalId, String destinationExternalId, BigDecimal value, String statementExternalId) {
        this.originExternalId = originExternalId;
        this.destinationExternalId = destinationExternalId;
        this.value = value;
        this.statementExternalId = statementExternalId;
    }

    public String getOriginExternalId() {
        return originExternalId;
    }

    public void setOriginExternalId(String originExternalId) {
        this.originExternalId = originExternalId;
    }

    public String getDestinationExternalId() {
        return destinationExternalId;
    }

    public void setDestinationExternalId(String destinationExternalId) {
        this.destinationExternalId = destinationExternalId;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getStatementExternalId() {
        return statementExternalId;
    }

    public void setStatementExternalId(String statementExternalId) {
        this.statementExternalId = statementExternalId;
    }

    @Override
    public String toString() {
        return "FinancialMovementDTO{" +
                "originExternalId='" + originExternalId + '\'' +
                ", destinationExternalId='" + destinationExternalId + '\'' +
                ", value=" + value +
                ", statementExternalId='" + statementExternalId + '\'' +
                '}';
    }
}
