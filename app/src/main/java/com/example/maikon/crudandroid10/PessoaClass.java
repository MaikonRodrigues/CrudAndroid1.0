package com.example.maikon.crudandroid10;

public class PessoaClass {

    private int cpf, telefone;
    private String nome, endereco;

    public PessoaClass(int cpf, int telefone, String nome, String endereco) {
        this.cpf = cpf;
        this.telefone = telefone;
        this.nome = nome;
        this.endereco = endereco;
    }

    public PessoaClass() {

    }

    public int getCpf() {
        return cpf;
    }

    public void setCpf(int cpf) {
        this.cpf = cpf;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public int getTelefone() {
        return telefone;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }




}
