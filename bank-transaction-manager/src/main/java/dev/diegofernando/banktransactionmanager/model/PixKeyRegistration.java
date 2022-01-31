package dev.diegofernando.banktransactionmanager.model;

import dev.diegofernando.banktransactionmanager.model.enums.TypePixKey;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "pix_key_registrations")
@NamedQueries({
        @NamedQuery(name = "PixKeyRegistration.findByBankAccount",
                query = "SELECT p FROM PixKeyRegistration p WHERE p.bankAccount=:bankAccount"),
        @NamedQuery(name = "PixKeyRegistration.findByBankByKey",
                query = "SELECT p FROM PixKeyRegistration p WHERE p.pixKey=:pixKey")
})
public class PixKeyRegistration implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "type_pix_key", length = 30)
    private TypePixKey typePixKey;

    @Column(nullable = false, name = "pix_key", unique = true, length = 100)
    private String pixKey;

    @OneToOne
    @JoinColumn(name = "bank_account_id")
    private BankAccount bankAccount;

    public PixKeyRegistration() {
    }

    public PixKeyRegistration(TypePixKey typePixKey, String pixKey, BankAccount bankAccount) {
        this.typePixKey = typePixKey;
        this.pixKey = pixKey;
        this.bankAccount = bankAccount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Override
    public String toString() {
        return "PixKeyRegistration{" +
                "id=" + id +
                ", typePixKey=" + typePixKey +
                ", pixKey='" + pixKey + '\'' +
                ", bankAccount=" + bankAccount +
                '}';
    }
}
