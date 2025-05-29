package com.sangue.model;

import java.time.LocalDateTime;

public class Notificacao {
    private Doador doador;
    private String mensagem;
    private LocalDateTime dataHora;

    public Notificacao(Doador doador, String mensagem) {
        this.doador = doador;
        this.mensagem = mensagem;
        this.dataHora = LocalDateTime.now();
    }

    public Doador getDoador() {
        return doador;
    }

    public String getMensagem() {
        return mensagem;
    }

    @Override
    public String toString() {
        return "Notificacao: \n" +
                "Doador: " + doador.getNomeCompleto() + "\n"+
                "Mensagem='" + mensagem;
    }
}
