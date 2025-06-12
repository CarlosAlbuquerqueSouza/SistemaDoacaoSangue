package br.com.sangue.main;

import br.com.sangue.model.EstoqueSangue;
import br.com.sangue.service.EstoqueService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EstoqueView extends JFrame {

    private final EstoqueService estoqueService;

    public EstoqueView(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
        inicializar();
    }

    private void inicializar() {
        setTitle("Visualização de Estoque de Sangue");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel titulo = new JLabel("Estoque Atual:");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        painel.add(titulo);
        painel.add(Box.createVerticalStrut(20));

        // Lista os estoques
        List<EstoqueSangue> lista = estoqueService.listarTodos();
        if (lista.isEmpty()) {
            JLabel vazio = new JLabel("Nenhum dado de estoque disponível.");
            vazio.setAlignmentX(Component.CENTER_ALIGNMENT);
            painel.add(vazio);
        } else {
            for (EstoqueSangue estoque : lista) {
                JLabel label = new JLabel(estoque.getTipo() + " → " + estoque.getQuantidadeUnidades() + " unidades");

                if (estoque.isCritico()) {
                    label.setForeground(Color.RED);
                    label.setFont(label.getFont().deriveFont(Font.BOLD));
                }

                painel.add(label);
                painel.add(Box.createVerticalStrut(10));
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
