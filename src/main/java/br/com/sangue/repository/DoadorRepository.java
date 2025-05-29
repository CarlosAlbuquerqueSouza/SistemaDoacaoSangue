package com.sangue.repository;

import com.sangue.model.Doador;

import java.util.List;

public interface DoadorRepository {
    void salvar(Doador doador);
    List<Doador> listarTodos();
    List<Doador> buscarPorTipoSanguineoDisponivel(String tipo);
}
