package br.com.sangue.main;

import br.com.sangue.model.Doador;
import br.com.sangue.model.EstoqueSangue;
import br.com.sangue.model.TipoSanguineo;
import br.com.sangue.repository.*;
import br.com.sangue.service.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Main {

    // Repositórios em memória
    private static DoadorRepository doadorRepository = new DoadorRepositoryEmMemoria();
    private static EstoqueSangueRepository estoqueRepository = new EstoqueSangueRepositoryEmMemoria();

    // Serviços
    private static DoadorService doadorService = new DoadorService(doadorRepository);
    private static EstoqueService estoqueService = new EstoqueService(estoqueRepository);
    private static NotificacaoService notificacaoService = new NotificacaoService();

    public static void main(String[] args) {
        estoqueService.atualizarEstoque(TipoSanguineo.A_POSITIVO, 15);
        estoqueService.atualizarEstoque(TipoSanguineo.A_NEGATIVO, 7);  // nível crítico
        estoqueService.atualizarEstoque(TipoSanguineo.B_POSITIVO, 12);
        estoqueService.atualizarEstoque(TipoSanguineo.O_NEGATIVO, 5);  // nível crítico
        estoqueService.atualizarEstoque(TipoSanguineo.AB_POSITIVO, 20);
        verificarEstoqueCriticoENotificar();
        SwingUtilities.invokeLater(Main::criarJanelaPrincipal);
    }

    private static void criarJanelaPrincipal() {
        JFrame frame = new JFrame("Painel do Administrador - Sistema de Doações");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null); // Centralizar janela

        // Painel principal
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60)); // Margens internas

        Dimension tamanhoBotao = new Dimension(300, 40);

        // Botão: Cadastrar novo doador
        JButton btnCadastrar = new JButton("Cadastrar Novo Doador");
        estilizarBotao(btnCadastrar, tamanhoBotao);
        btnCadastrar.addActionListener(e -> {
            new CadastroDoadorView(doadorService, estoqueService, notificacaoService);
        });

        // Botão: Visualizar estoque
        JButton btnEstoque = new JButton("Visualizar Estoque de Sangue");
        estilizarBotao(btnEstoque, tamanhoBotao);
        btnEstoque.addActionListener(e -> {
            new EstoqueView(estoqueService);
        });

        // Botão: Visualizar notificações
        JButton btnNotificacoes = new JButton("Visualizar Notificações");
        estilizarBotao(btnNotificacoes, tamanhoBotao);
        btnNotificacoes.addActionListener(e -> {
            new NotificacaoView(notificacaoService);
        });

        // Botão: Sair
        JButton btnSair = new JButton("Sair");
        estilizarBotao(btnSair, tamanhoBotao);
        btnSair.setBackground(Color.DARK_GRAY);
        btnSair.setForeground(Color.WHITE);
        btnSair.addActionListener(e -> System.exit(0));

        // Adicionando componentes com espaçamentos
        painel.add(btnCadastrar);
        painel.add(Box.createVerticalStrut(15));
        painel.add(btnEstoque);
        painel.add(Box.createVerticalStrut(15));
        painel.add(btnNotificacoes);
        painel.add(Box.createVerticalStrut(30));
        painel.add(btnSair);

        frame.add(painel);
        frame.setVisible(true);
    }

    // Método auxiliar para padronizar botões
    private static void estilizarBotao(JButton botao, Dimension tamanho) {
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);
        botao.setMaximumSize(tamanho);
        botao.setFocusPainted(false);
    }

    private static void verificarEstoqueCriticoENotificar() {
        List<EstoqueSangue> criticos = estoqueService.listarCriticos();

        for (EstoqueSangue estoque : criticos) {
            List<Doador> doadores = doadorService.buscarDisponiveisPorTipo(estoque.getTipo());

            if (!doadores.isEmpty()) {
                notificacaoService.notificarMultiplos(doadores);
            }
        }
    }
}
