package br.com.sangue.main;

import br.com.sangue.model.Notificacao;
import br.com.sangue.service.NotificacaoService;
import br.com.sangue.model.Doador;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class NotificacaoView extends JFrame {

    private final NotificacaoService notificacaoService;

    public NotificacaoView(NotificacaoService notificacaoService) {
        this.notificacaoService = notificacaoService;
        inicializar();
    }

    private void inicializar() {
        setTitle("Notificações Enviadas");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel titulo = new JLabel("Notificações aos Doadores");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        painel.add(titulo);
        painel.add(Box.createVerticalStrut(20));

        List<Notificacao> lista = notificacaoService.listarTodas();

        if (lista.isEmpty()) {
            JLabel vazio = new JLabel("Nenhuma notificação enviada ainda.");
            vazio.setAlignmentX(Component.CENTER_ALIGNMENT);
            painel.add(vazio);
        } else {
            for (Notificacao n : lista) {
                JPanel bloco = new JPanel();
                bloco.setLayout(new BoxLayout(bloco, BoxLayout.Y_AXIS));
                bloco.setAlignmentX(Component.CENTER_ALIGNMENT);
                bloco.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                JLabel nomeLabel = new JLabel("Nome: " + n.getDoador().getNomeCompleto());
                nomeLabel.setFont(new Font("Arial", Font.BOLD, 14));
                nomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

                JLabel msgLabel = new JLabel("Mensagem: " + n.getMensagem());
                msgLabel.setFont(new Font("Arial", Font.PLAIN, 13));
                msgLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

                bloco.add(nomeLabel);
                bloco.add(Box.createVerticalStrut(4));
                bloco.add(msgLabel);

                painel.add(bloco);
                painel.add(Box.createVerticalStrut(15)); // Espaçamento entre notificações
            }
        }

        painel.add(Box.createVerticalStrut(20));
        JButton btnFechar = new JButton("Fechar");
        btnFechar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnFechar.addActionListener(e -> dispose());

        painel.add(btnFechar);
        add(painel);
        setVisible(true);
    }
}
