package br.com.sangue.repository;

import br.com.sangue.model.Doador;

import java.util.List;

public interface DoadorRepository {
    void salvar(Doador doador);
    List<Doador> buscarPorTipoSanguineoDisponivel(String tipo);
}
