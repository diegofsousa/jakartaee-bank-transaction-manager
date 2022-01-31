package dev.diegofernando.banktransactionmanager.model;

import dev.diegofernando.banktransactionmanager.model.enums.TypeAccount;
import dev.diegofernando.banktransactionmanager.util.RandomStringGeneratorUtils;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "clients")
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({
        @NamedQuery(name = "Client.listAllByCreatedAtAsc",
                query = "SELECT c FROM Client c ORDER BY c.createdAt ASC"),
        @NamedQuery(name = "Client.findByExternalId",
                query = "SELECT c FROM Client c WHERE c.externalId=:externalId")
})
@NamedNativeQueries({
        @NamedNativeQuery(name = "Client.deleteNaturalPersonById",
                query = "DELETE FROM appdb.natural_persons WHERE id = ?",
                resultClass = Client.class
        ),
        @NamedNativeQuery(name = "Client.deleteLegalEntityById",
                query = "DELETE FROM appdb.legal_entities WHERE id = ?",
                resultClass = Client.class
        ),
        @NamedNativeQuery(name = "Client.insertNaturalPersonWithClientAlreadyRegistered",
                query = "INSERT INTO appdb.natural_persons (cpf, name, id) VALUES (?, ?, ?)",
                resultClass = Client.class
        ),
        @NamedNativeQuery(name = "Client.insertLegalEntityWithClientAlreadyRegistered",
                query = "INSERT INTO appdb.legal_entities (cnpj, social_name, id) VALUES (?, ?, ?)",
                resultClass = Client.class
        )
})
public abstract class Client implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    private Long id;

    @Column(nullable = false, name = "external_id", unique = true, length = 60)
    private String externalId;

    @Column(nullable = false, name = "phone_number", length = 25)
    private String phoneNumber;

    @CreationTimestamp
    @Column(nullable = false, name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, name = "updated_at")
    private Date updatedAt;

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void generateExternalId() {
        setExternalId(RandomStringGeneratorUtils.getHashFromString());
    }

    public TypeAccount typeAccount(){
        return this instanceof NaturalPerson ? TypeAccount.NATURAL_PERSON :
                TypeAccount.LEGAL_ENTITY;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", externalId='" + externalId + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
