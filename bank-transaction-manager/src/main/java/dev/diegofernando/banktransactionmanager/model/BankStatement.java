package dev.diegofernando.banktransactionmanager.model;

import dev.diegofernando.banktransactionmanager.model.enums.TypeChannelTransaction;
import dev.diegofernando.banktransactionmanager.model.enums.TypeMovementTransaction;
import dev.diegofernando.banktransactionmanager.util.RandomStringGeneratorUtils;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "bank_statements")
@NamedQueries({
        @NamedQuery(name = "BankStatement.findByExternalId",
                query = "SELECT b FROM BankStatement b WHERE b.externalId=:externalId"),
        @NamedQuery(name = "BankStatement.sumOfTheValueTransactionsTodayByType",
                query = "SELECT SUM(b.value) FROM BankStatement b WHERE b.bankAccount=:bankAccount AND b" +
                        ".typeChannelTransaction=:typeChannelTransaction AND b" +
                        ".typeMovementTransaction=:typeMovementTransaction AND b.createdAt > CURRENT_DATE")
})
public class BankStatement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    private Long id;

    @Column(nullable = false, name = "external_id", unique = true, length = 60)
    private String externalId;

    @CreationTimestamp
    @Column(nullable = false, name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(nullable = false, name = "value")
    private BigDecimal value;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "type_channel_transaction", length = 100)
    private TypeChannelTransaction typeChannelTransaction;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "type_movement_transaction", length = 100)
    private TypeMovementTransaction typeMovementTransaction;

    @ManyToOne
    @JoinColumn(name = "bank_account_id")
    private BankAccount bankAccount;

    public BankStatement() {
    }

    public BankStatement(BigDecimal value,
                         TypeChannelTransaction typeChannelTransaction,
                         TypeMovementTransaction typeMovementTransaction, BankAccount bankAccount) {
        this.value = value;
        this.typeChannelTransaction = typeChannelTransaction;
        this.typeMovementTransaction = typeMovementTransaction;
        this.bankAccount = bankAccount;
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

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public void generateExternalId() {
        setExternalId(RandomStringGeneratorUtils.getHashFromString());
    }


    @Override
    public String toString() {
        return "BankStatement{" +
                "id=" + id +
                ", externalId='" + externalId + '\'' +
                ", createdAt=" + createdAt +
                ", value=" + value +
                ", typeChannelTransaction=" + typeChannelTransaction +
                ", typeMovementTransaction=" + typeMovementTransaction +
                ", bankAccount=" + bankAccount +
                '}';
    }
}
