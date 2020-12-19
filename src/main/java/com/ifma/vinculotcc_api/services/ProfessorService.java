package com.ifma.vinculotcc_api.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import javax.transaction.Transactional;

import com.ifma.vinculotcc_api.models.Professor;
import com.ifma.vinculotcc_api.repositories.ProfessorRepository;
import com.ifma.vinculotcc_api.repositories.filters.ProfessorFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfessorService {
    private final ProfessorRepository professorRepository;

    @Autowired
    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    @Transactional
    public Professor salva(Professor professor) {
        return this.professorRepository.save(professor);
    }

    public Iterable<Professor> todos() {
        return this.professorRepository.findAll();
    }

    public Iterable<Professor> buscarPorNome(String nome) {
        return this.professorRepository.findByNome(nome);
    }

    public Iterable<Professor> buscarPorArea(String area) {
        return this.professorRepository.findByArea(area);
    }

    public Optional<Professor> buscarUmProfessorPorMatricula(String matricula) {
        return this.professorRepository.buscarUmPorMatricula(matricula);
    }

    /*
     * Versão 1 public Cliente buscarPor(Long id) { return
     * clienteRepository.findById(id).get(); }
     */

    // Versão 2
    public Optional<Professor> buscarPor(Long id) {
        return this.professorRepository.findById(id);
    }

    @Transactional
    public void removePelo(Long id) {
        this.professorRepository.deleteById(id);
    }

    public Iterable<Professor> filtrar(ProfessorFilter filtro) {
        ArrayList<Professor> professores_validos = new ArrayList<>();

        
        String nome = filtro.getNome() == null ? "%" : filtro.getNome();
        Iterable<Professor> professores =  this.professorRepository.findByNomeContaining(nome);
        
        

        Iterator<Professor> i = professores.iterator();
        while (i.hasNext()) {
            Professor p = (Professor) i.next();
            if (p.getTccs().size() < 7) {
                professores_validos.add(p);
            }
        }
        return professores_validos;
    }
}
