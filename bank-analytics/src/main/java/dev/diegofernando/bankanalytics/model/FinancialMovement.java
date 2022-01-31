package dev.diegofernando.bankanalytics.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "financial_movements")
public class FinancialMovement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    private Long id;

    @Column(nullable = false, name = "origin_external_id", unique = false, length = 60)
    private String originExternalId;

    @Column(nullable = false, name = "destination_external_id", unique = false, length = 60)
    private String destinationExternalId;

    @Column(nullable = false, name = "value")
    private BigDecimal value;

    @Column(nullable = false, name = "statement_external_id", unique = true, length = 60)
    private String statementExternalId;

    public FinancialMovement() {
    }

    public FinancialMovement(String originExternalId, String destinationExternalId, BigDecimal value, String statementExternalId) {
        this.originExternalId = originExternalId;
        this.destinationExternalId = destinationExternalId;
        this.value = value;
        this.statementExternalId = statementExternalId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return "FinancialMovement{" +
                "id=" + id +
                ", originExternalId='" + originExternalId + '\'' +
                ", destinationExternalId='" + destinationExternalId + '\'' +
                ", value=" + value +
                ", statementExternalId='" + statementExternalId + '\'' +
                '}';
    }
}
