package com.sangue.model;

public class EstoqueSangue {

    private TipoSanguineo tipo;
    private int quantidadeUnidades; // Em número de bolsas

    public EstoqueSangue(TipoSanguineo tipo, int quantidadeUnidades) {
        this.tipo = tipo;
        this.quantidadeUnidades = quantidadeUnidades;
    }

    public TipoSanguineo getTipo() {
        return tipo;
    }

    public void setTipo(TipoSanguineo tipo) {
        this.tipo = tipo;
    }

    public int getQuantidadeUnidades() {
        return quantidadeUnidades;
    }

    public void setQuantidadeUnidades(int quantidadeUnidades) {
        this.quantidadeUnidades = quantidadeUnidades;
    }

    @Override
    public String toString() {
        return "EstoqueSangue: \n" +
                "Tipo: " + tipo + "\n" +
                "Quantidade: " + quantidadeUnidades;
    }
}
