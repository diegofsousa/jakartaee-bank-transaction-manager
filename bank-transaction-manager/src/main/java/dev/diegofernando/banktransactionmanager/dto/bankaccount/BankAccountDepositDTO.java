package dev.diegofernando.banktransactionmanager.dto.bankaccount;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

public class BankAccountDepositDTO {

    @NotBlank
    private String externalId;

    private BigDecimal value;

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "BankAccountDepositDTO{" +
                "externalId='" + externalId + '\'' +
                ", value=" + value +
                '}';
    }
}
