package br.com.sangue.model;

public class Doador {

    private String nomeCompleto;
    private int idade;
    private double peso;
    private TipoSanguineo tipoSanguineo;
    private boolean disponibilidade;

    public Doador(String nomeCompleto, int idade, double peso, TipoSanguineo tipoSanguineo, boolean disponibilidade) {
        this.nomeCompleto = nomeCompleto;
        this.idade = idade;
        this.peso = peso;
        this.tipoSanguineo = tipoSanguineo;
        this.disponibilidade = disponibilidade;
    }

    public boolean isDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(boolean disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    public TipoSanguineo getTipoSanguineo() {
        return tipoSanguineo;
    }

    public void setTipoSanguineo(TipoSanguineo tipoSanguineo) {
        this.tipoSanguineo = tipoSanguineo;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    @Override
    public String toString() {
        return "Doador: \n" +
                "Nome: " + nomeCompleto + "\n" +
                "Idade: " + idade + "\n" +
                "Peso:" + peso +  "\n" +
                "Tipo: " + tipoSanguineo +  "\n" +
                "Disponível: " + disponibilidade + "\n";
                }

}
