package dev.diegofernando.banktransactionmanager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "legal_entities")
public class LegalEntity extends Client {

    @Column(nullable = false, name = "social_name", length = 150)
    private String socialName;

    @Column(nullable = false, name = "cnpj", unique = true, length = 14)
    private String cnpj;

    public LegalEntity() {
    }

    public LegalEntity(String socialName, String cnpj) {
        this.socialName = socialName;
        this.cnpj = cnpj;
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

    @Override
    public String toString() {
        return "LegalEntity{" +
                "socialName='" + socialName + '\'' +
                ", cnpj='" + cnpj + '\'' +
                '}';
    }
}
