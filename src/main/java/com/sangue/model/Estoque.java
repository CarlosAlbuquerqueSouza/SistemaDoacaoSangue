package com.sangue.model;

public class Estoque {
    private String tipoSanguineo;
    private int bolsasDisponiveis;
    private int limiteMinimo;

    public boolean isCritico() {
        return bolsasDisponiveis < limiteMinimo;
    }

    public String getTipoSanguineo() {
        return tipoSanguineo;
    }

    public void setTipoSanguineo(String tipoSanguineo) {
        this.tipoSanguineo = tipoSanguineo;
    }

    public int getBolsasDisponiveis() {
        return bolsasDisponiveis;
    }

    public void setBolsasDisponiveis(int bolsasDisponiveis) {
        this.bolsasDisponiveis = bolsasDisponiveis;
    }

    public int getLimiteMinimo() {
        return limiteMinimo;
    }

    public void setLimiteMinimo(int limiteMinimo) {
        this.limiteMinimo = limiteMinimo;
    }
}
