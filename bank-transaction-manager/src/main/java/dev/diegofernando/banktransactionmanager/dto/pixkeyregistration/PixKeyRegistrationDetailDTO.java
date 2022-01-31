package dev.diegofernando.banktransactionmanager.dto.pixkeyregistration;

import dev.diegofernando.banktransactionmanager.dto.bankaccount.BankAccountSimpleDTO;
import dev.diegofernando.banktransactionmanager.model.BankAccount;
import dev.diegofernando.banktransactionmanager.model.enums.TypePixKey;

public class PixKeyRegistrationDetailDTO {

    private TypePixKey typePixKey;
    private String pixKey;
    private BankAccountSimpleDTO bankAccount;

    public TypePixKey getTypePixKey() {
        return typePixKey;
    }

    public void setTypePixKey(TypePixKey typePixKey) {
        this.typePixKey = typePixKey;
    }

    public String getPixKey() {
        return pixKey;
    }

    public void setPixKey(String pixKey) {
        this.pixKey = pixKey;
    }

    public BankAccountSimpleDTO getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccountSimpleDTO bankAccount) {
        this.bankAccount = bankAccount;
    }
}
