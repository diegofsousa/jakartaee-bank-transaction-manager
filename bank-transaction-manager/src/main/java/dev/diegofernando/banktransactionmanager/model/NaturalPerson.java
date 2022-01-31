package dev.diegofernando.banktransactionmanager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "natural_persons")
public class NaturalPerson extends Client {

    @Column(nullable = false, name = "name", length = 150)
    private String name;

    @Column(nullable = false, name = "cpf", unique = true, length = 11)
    private String cpf;

    public NaturalPerson() {
    }

    public NaturalPerson(String name, String cpf) {
        this.name = name;
        this.cpf = cpf;
    }

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

    @Override
    public String toString() {
        return "NaturalPerson{" +
                "name='" + name + '\'' +
                ", cpf='" + cpf + '\'' +
                '}';
    }
}
