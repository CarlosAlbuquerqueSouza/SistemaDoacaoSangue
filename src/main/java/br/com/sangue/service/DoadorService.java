package br.com.sangue.service;

import br.com.sangue.model.Doador;
import br.com.sangue.model.TipoSanguineo;
import br.com.sangue.repository.DoadorRepository;

import java.util.List;

public class DoadorService {

    private final DoadorRepository doadorRepository;


    public DoadorService(DoadorRepository doadorRepository) {
        this.doadorRepository = doadorRepository;
    }

    public void cadastrarDoador(String nome, int idade, double peso, TipoSanguineo tipo, boolean disponibilidade){
        if(idade >= 16 && idade <= 69){
            Doador doador = new Doador(nome, idade, peso, tipo, disponibilidade);
            doadorRepository.salvar(doador);
        }else {
            throw new IllegalArgumentException("Doador não atende aos critérios mínimos.");
        }
    }

    public List<Doador> buscarDisponiveisPorTipo(TipoSanguineo tipo) {
        return doadorRepository.buscarPorTipoSanguineoDisponivel(tipo.name());
    }
}
