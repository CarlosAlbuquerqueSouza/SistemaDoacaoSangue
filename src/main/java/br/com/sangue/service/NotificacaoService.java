package br.com.sangue.service;

import br.com.sangue.model.Doador;
import br.com.sangue.model.Notificacao;

import java.util.ArrayList;
import java.util.List;

public class NotificacaoService {

    private final List<Notificacao> notificacoes = new ArrayList<>();
    private static final String MENSAGEM_PADRAO = "⚠ Seu tipo sanguíneo está em falta. ⚠ \n Por favor, compareça ao centro de doação!";

    public void notificar(Doador doador) {
        Notificacao notificacao = new Notificacao(doador, MENSAGEM_PADRAO);
        notificacoes.add(notificacao);
    }

    public List<Notificacao> listarTodas() {
        return new ArrayList<>(notificacoes);
    }

    public void notificarMultiplos(List<Doador> doadores) {
        for (Doador doador : doadores) {
            notificar(doador);
        }
    }
}
