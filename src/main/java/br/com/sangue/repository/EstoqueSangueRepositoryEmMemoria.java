package br.com.sangue.repository;

import br.com.sangue.model.EstoqueSangue;
import br.com.sangue.model.TipoSanguineo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EstoqueSangueRepositoryEmMemoria implements EstoqueSangueRepository {

    private final Map<TipoSanguineo, EstoqueSangue> estoqueMap = new HashMap<>();

    @Override
    public void atualizarEstoque(EstoqueSangue estoque) {
        estoqueMap.put(estoque.getTipo(), estoque);
    }

    @Override
    public EstoqueSangue buscarPorTipo(TipoSanguineo tipo) {
        return estoqueMap.getOrDefault(tipo, new EstoqueSangue(tipo, 0));
    }

    @Override
    public List<EstoqueSangue> listarTodos() {
        return new ArrayList<>(estoqueMap.values());
    }
}
