package dev.diegofernando.banktransactionmanager.dto.pixkeyregistration;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class PixTransferDTO {

    @NotBlank
    private String keyOrigin;

    @NotBlank
    private String keyDestination;

    @NotNull
    private BigDecimal value;

    public String getKeyOrigin() {
        return keyOrigin;
    }

    public void setKeyOrigin(String keyOrigin) {
        this.keyOrigin = keyOrigin;
    }

    public String getKeyDestination() {
        return keyDestination;
    }

    public void setKeyDestination(String keyDestination) {
        this.keyDestination = keyDestination;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
