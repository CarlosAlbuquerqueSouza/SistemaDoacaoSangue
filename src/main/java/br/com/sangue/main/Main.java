package br.com.sangue;

import br.com.sangue.repository.*;
import br.com.sangue.service.*;

import javax.swing.*;
import java.awt.*;

public class Main {

    // Repositórios em memória
    private static DoadorRepository doadorRepository = new DoadorRepositoryEmMemoria();
    private static EstoqueSangueRepository estoqueRepository = new EstoqueSangueRepositoryEmMemoria();

    // Serviços
    private static DoadorService doadorService = new DoadorService(doadorRepository);
    private static EstoqueService estoqueService = new EstoqueService(estoqueRepository);
    private static NotificacaoService notificacaoService = new NotificacaoService();

    public static void main(String[] args) {
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
            JOptionPane.showMessageDialog(frame, "Tela de Cadastro será implementada.");
        });

        // Botão: Visualizar estoque
        JButton btnEstoque = new JButton("Visualizar Estoque de Sangue");
        estilizarBotao(btnEstoque, tamanhoBotao);
        btnEstoque.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Tela de Estoque será implementada.");
        });

        // Botão: Visualizar notificações
        JButton btnNotificacoes = new JButton("Visualizar Notificações");
        estilizarBotao(btnNotificacoes, tamanhoBotao);
        btnNotificacoes.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Tela de Notificações será implementada.");
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
}
