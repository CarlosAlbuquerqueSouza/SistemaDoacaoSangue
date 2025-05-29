package com.sangue.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Pessoa {

    private Long id;
    private String nomeCompleto;
    private String cpf; 
    private String senha;
    private int idade;
    private double peso;
    private String tipoSanguineo;
    private LocalDate ultimaDoacao;

    public boolean podeDoar (){
        return ultimaDoacao == null || ChronoUnit.DAYS.between(ultimaDoacao, LocalDate.now()) >= 30;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getTipoSanguineo() {
        return tipoSanguineo;
    }

    public void setTipoSanguineo(String tipoSanguineo) {
        this.tipoSanguineo = tipoSanguineo;
    }

    public LocalDate getUltimaDoacao() {
        return ultimaDoacao;
    }

    public void setUltimaDoacao(LocalDate ultimaDoacao) {
        this.ultimaDoacao = ultimaDoacao;
    }
}
