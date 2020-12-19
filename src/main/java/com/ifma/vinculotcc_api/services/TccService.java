package com.ifma.vinculotcc_api.service;

import java.util.Optional;

import javax.transaction.Transactional;

import com.ifma.vinculotcc_api.models.Tcc;
import com.ifma.vinculotcc_api.repositories.TccRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TccService {
    private final TccRepository tccRepository;
    
    private boolean tcc_valido = true;

    @Autowired
    public TccService(TccRepository tccRepository) {
        this.tccRepository = tccRepository;
    }

    public boolean validar_alunoTcc(AlunoService alunoService, Long id_aluno) {
        this.todos().forEach(tcc-> {
            if(tcc.getAlunos().contains(alunoService.buscarPor(id_aluno).get())) {
                this.tcc_valido = false;
            }
        });
        return tcc_valido;
    }

    @Transactional
    public Tcc salva(Tcc tcc) {
        return this.tccRepository.save(tcc);
    }

    public Iterable<Tcc> todos() {
        return tccRepository.findAll();
    }

    public Iterable<Tcc> buscarPorTema(String tema) {
        return tccRepository.findByTema(tema);
    }

    public Iterable<Tcc> buscarPorArea(String area) {
        return tccRepository.findByArea(area);
    }

    /*
     * Versão 1 public Cliente buscarPor(Long id) { return
     * clienteRepository.findById(id).get(); }
     */

    // Versão 2
    public Optional<Tcc> buscarPor(Long id) {
        return tccRepository.findById(id);
    }


    @Transactional
    public void removePelo(Long id) {
        tccRepository.deleteById(id);
    }

    public Tcc buscarUm(Long id) {
        return tccRepository.getOne(id);
    }

}
