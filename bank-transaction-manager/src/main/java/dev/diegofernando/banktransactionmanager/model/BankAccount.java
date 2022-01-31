package dev.diegofernando.banktransactionmanager.model;

import dev.diegofernando.banktransactionmanager.model.enums.TypeAccount;
import dev.diegofernando.banktransactionmanager.util.RandomStringGeneratorUtils;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "bank_accounts")
@NamedQueries({
        @NamedQuery(name = "BankAccount.listByAgencyOpeningAtDesc",
                query = "SELECT b FROM BankAccount b WHERE b.agency=:agency ORDER BY b.openingAt DESC"),
        @NamedQuery(name = "BankAccount.findByExternalId",
                query = "SELECT b FROM BankAccount b WHERE b.externalId=:externalId")
})
public class BankAccount implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    private Long id;

    @Column(nullable = false, name = "external_id", unique = true, length = 60)
    private String externalId;

    @Column(nullable = false, name = "number", unique = false, length = 9)
    private String number;

    @Column(nullable = false, name = "agency", unique = false, length = 4)
    private String agency;

    @CreationTimestamp
    @Column(nullable = false, name = "opening_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date openingAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, name = "updated_at")
    private Date updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "type_account", length = 40)
    private TypeAccount typeAccount;

    @Column(nullable = false, name = "active")
    private Boolean active = Boolean.TRUE;

    @Column(nullable = false, name = "balance")
    private BigDecimal balance = BigDecimal.ZERO;

    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "bankAccount")
    private List<BankStatement> bankStatements;

    public BankAccount() {
    }

    public BankAccount(String number, String agency, TypeAccount typeAccount, Client client) {
        this.number = number;
        this.agency = agency;
        this.typeAccount = typeAccount;
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<BankStatement> getBankStatements() {
        return bankStatements;
    }

    public void setBankStatements(List<BankStatement> bankStatements) {
        this.bankStatements = bankStatements;
    }

    public void generateExternalId() {
        setExternalId(RandomStringGeneratorUtils.getHashFromString());
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "id=" + id +
                ", externalId='" + externalId + '\'' +
                ", number='" + number + '\'' +
                ", agency='" + agency + '\'' +
                ", openingAt=" + openingAt +
                ", updatedAt=" + updatedAt +
                ", typeAccount=" + typeAccount +
                ", active=" + active +
                ", balance=" + balance +
                ", client=" + client +
                ", bankStatements=" + bankStatements +
                '}';
    }
}
