package br.com.sangue.repository;

import br.com.sangue.model.EstoqueSangue;
import br.com.sangue.model.TipoSanguineo;

import java.util.List;

public interface EstoqueSangueRepository {
    void atualizarEstoque(EstoqueSangue estoque);
    EstoqueSangue buscarPorTipo(TipoSanguineo tipo);
    List<EstoqueSangue> listarTodos();
}
