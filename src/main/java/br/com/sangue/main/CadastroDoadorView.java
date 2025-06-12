package br.com.sangue.main;

import br.com.sangue.model.Doador;
import br.com.sangue.model.EstoqueSangue;
import br.com.sangue.model.TipoSanguineo;
import br.com.sangue.service.DoadorService;
import br.com.sangue.service.EstoqueService;
import br.com.sangue.service.NotificacaoService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CadastroDoadorView extends JFrame {

    private final DoadorService doadorService;
    private final EstoqueService estoqueService;
    private final NotificacaoService notificacaoService;

    public CadastroDoadorView(DoadorService doadorService, EstoqueService estoqueService, NotificacaoService notificacaoService) {
        this.doadorService = doadorService;
        this.estoqueService = estoqueService;
        this.notificacaoService = notificacaoService;
        inicializar();
    }

    private void inicializar() {
        setTitle("Cadastro de Novo Doador");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Nome
        JTextField txtNome = new JTextField();
        painel.add(new JLabel("Nome completo:"));
        painel.add(txtNome);

        // Idade
        JTextField txtIdade = new JTextField();
        painel.add(new JLabel("Idade:"));
        painel.add(txtIdade);

        // Peso
        JTextField txtPeso = new JTextField();
        painel.add(new JLabel("Peso (kg):"));
        painel.add(txtPeso);

        // Tipo sanguíneo
        JComboBox<TipoSanguineo> cmbTipo = new JComboBox<>(TipoSanguineo.values());
        painel.add(new JLabel("Tipo sanguíneo:"));
        painel.add(cmbTipo);

        // Disponibilidade
        JCheckBox chkDisponivel = new JCheckBox("Disponível para doação");
        painel.add(chkDisponivel);

        painel.add(Box.createVerticalStrut(15));

        // Botões
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnSalvar = new JButton("Cadastrar");
        JButton btnCancelar = new JButton("Cancelar");

        btnSalvar.addActionListener(e -> {
            try {
                String nome = txtNome.getText().trim();
                int idade = Integer.parseInt(txtIdade.getText().trim());
                double peso = Double.parseDouble(txtPeso.getText().trim());
                TipoSanguineo tipo = (TipoSanguineo) cmbTipo.getSelectedItem();
                boolean disponibilidade = chkDisponivel.isSelected();

                doadorService.cadastrarDoador(nome, idade, peso, tipo, disponibilidade);

                JOptionPane.showMessageDialog(this, "✅ Doador cadastrado com sucesso!");
                verificarEstoqueCritico();
                dispose(); // Fecha a janela

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "❗ Idade e peso devem ser numéricos.");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "❗ Erro: " + ex.getMessage());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❗ Erro inesperado: " + ex.getMessage());
            }
        });

        btnCancelar.addActionListener(e -> dispose());

        botoes.add(btnSalvar);
        botoes.add(btnCancelar);

        painel.add(Box.createVerticalStrut(10));
        painel.add(botoes);

        add(painel);
        setVisible(true);
    }

    private void verificarEstoqueCritico() {
        List<EstoqueSangue> criticos = estoqueService.listarCriticos();

        for (EstoqueSangue estoque : criticos) {
            List<Doador> doadores = doadorService.buscarDisponiveisPorTipo(estoque.getTipo());
            notificacaoService.notificarMultiplos(doadores);
        }
    }
}
