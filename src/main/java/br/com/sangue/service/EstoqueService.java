package br.com.sangue.service;

import br.com.sangue.model.EstoqueSangue;
import br.com.sangue.model.TipoSanguineo;
import br.com.sangue.repository.EstoqueSangueRepository;

import java.util.List;
import java.util.stream.Collectors;

public class EstoqueService {

    private final EstoqueSangueRepository estoqueSangueRepository;

    public EstoqueService(EstoqueSangueRepository estoqueSangueRepository) {
        this.estoqueSangueRepository = estoqueSangueRepository;
    }

    public void atualizarEstoque(TipoSanguineo tipo, int novaQuantidade) {
        EstoqueSangue estoque = new EstoqueSangue(tipo, novaQuantidade);
        estoqueSangueRepository.atualizarEstoque(estoque);
    }

    public EstoqueSangue buscarPorTipo(TipoSanguineo tipo) {
        return estoqueSangueRepository.buscarPorTipo(tipo);
    }

    public List<EstoqueSangue> listarTodos() {
        return estoqueSangueRepository.listarTodos();
    }

    public List<EstoqueSangue> listarCriticos() {
        return estoqueSangueRepository.listarTodos()
                .stream()
                .filter(EstoqueSangue::isCritico)
                .collect(Collectors.toList());
    }


}
