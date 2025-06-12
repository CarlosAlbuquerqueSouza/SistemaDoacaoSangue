package br.com.sangue.repository;

import br.com.sangue.model.Doador;
import br.com.sangue.model.TipoSanguineo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DoadorRepositoryEmMemoria implements DoadorRepository {

    private final List<Doador> doadores = new ArrayList<>();

    @Override
    public void salvar(Doador doador) {
        doadores.add(doador);
    }

    @Override
    public List<Doador> buscarPorTipoSanguineoDisponivel(String tipo) {
        TipoSanguineo tipoSanguineo = TipoSanguineo.valueOf(tipo);
        return doadores.stream()
                .filter(d -> d.isDisponibilidade() && d.getTipoSanguineo() == tipoSanguineo)
                .collect(Collectors.toList());
    }
}
