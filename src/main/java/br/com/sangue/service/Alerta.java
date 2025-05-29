package com.sangue.service;

import java.time.LocalDateTime;
import java.util.List;

public class Alerta {
    private Long id;
    private String tipoSanguineo;
    private String mensagem;
    private LocalDateTime dataEnvio;
    private List<Pessoa> destinatarios;

    // Gerar mensagem automática com base no tipo sanguíneo
    public static Alerta gerar(Estoque estoque, List<Pessoa> doadores) {
        Alerta alerta = new Alerta();
        alerta.tipoSanguineo = estoque.getTipoSanguineo();
        alerta.mensagem = "Urgente: Estoque do tipo " + estoque.getTipoSanguineo() + " está abaixo do mínimo!";
        alerta.dataEnvio = LocalDateTime.now();
        alerta.destinatarios = doadores;
        return alerta;
    }
}
