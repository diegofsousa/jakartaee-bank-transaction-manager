package dev.diegofernando.banktransactionmanager.dto.client;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class ClientSimpleDTO {
    private String name;

    @Pattern(regexp = "^[\\d]+$", message = "Apenas valores numéricos devem ser digitados")
    private String cpf;

    private String socialName;

    @Pattern(regexp = "^[\\d]+$", message = "Apenas valores numéricos devem ser digitados")
    private String cnpj;

    private String externalId;

    @NotBlank
    @Pattern(regexp = "^[\\d]+$", message = "Apenas valores numéricos devem ser digitados")
    private String phoneNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSocialName() {
        return socialName;
    }

    public void setSocialName(String socialName) {
        this.socialName = socialName;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "ClientRegisterDTO{" +
                "name='" + name + '\'' +
                ", cpf='" + cpf + '\'' +
                ", socialName='" + socialName + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", externalId='" + externalId + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
