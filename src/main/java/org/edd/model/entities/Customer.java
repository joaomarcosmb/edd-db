package org.edd.model.entities;

public class Customer {
    private int id;
    private String name;
    private String cpf;
    private String birthDate;
    private String email;

    public Customer(int id, String name, String cpf, String birthDate, String email) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.birthDate = birthDate;
        this.email = email;
    }

    public String getAllAttributes() {
        return "ID: " + this.id + ", Nome: " + this.name + ", CPF: " + this.cpf + ", Data de Nascimento: " + this.birthDate + ", Email: " + this.email;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}