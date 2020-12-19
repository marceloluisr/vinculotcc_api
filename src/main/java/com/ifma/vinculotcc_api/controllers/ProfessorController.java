package com.ifma.vinculotcc_api.controllers;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.ifma.vinculotcc_api.models.Professor;
import com.ifma.vinculotcc_api.models.Tcc;
import com.ifma.vinculotcc_api.repositories.filters.ProfessorFilter;
import com.ifma.vinculotcc_api.services.ProfessorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/professores")
public class ProfessorController {
    @Autowired
    private ProfessorService professorService;

    @RequestMapping("/novo")
    public String novo() {
        return "CadastroProfessor";
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView cadastrar(Professor prof) {
        this.professorService.salva(prof);

        ModelAndView mv = new ModelAndView("CadastroProfessor");
        mv.addObject("mensagem", "Professor cadastrado com sucesso!");
        return mv;
    }

    private ModelAndView consultarSolicitacoes(Professor professor) { 
        ModelAndView mv = new ModelAndView("Solicitacoes");

        Set<Tcc> tccs = professor.getTccs();

        if (tccs.isEmpty()) {
            mv.addObject("msgProfessor", "Você não tem solicitações."); 
            return mv;               
        }

        mv.addObject("solicitacoes", tccs);
        return mv;
    }

    @PostMapping("/autentica-professor")
    public ModelAndView autenticar_professor(@RequestParam("matricula") String matricula) {
        Optional<Professor> optional = professorService.buscarUmProfessorPorMatricula(matricula);
        if (optional.isPresent()) {
            return this.consultarSolicitacoes(optional.get());
        } else {
            ModelAndView mv = new ModelAndView("LoginProfessor");
            mv.addObject("mensagem", "Usuário não encontrado");
            return mv;
        }
    }

    @RequestMapping("/pesquisar/{id_aluno}") 
	public ModelAndView pesquisar(@ModelAttribute("filtro") ProfessorFilter filtro, @PathVariable("id_aluno") Long id_aluno) {
      
		List<Professor> todosProfessores = (List<Professor>) professorService.filtrar(filtro);

		ModelAndView mv = new ModelAndView("BuscadorProfessor");
        mv.addObject("professores", todosProfessores);
        mv.addObject("aluno_id", id_aluno);
		return mv;
	}

    @GetMapping("/{id}")
    public ResponseEntity<Professor> buscaPor(@PathVariable Long id) {
        Optional<Professor> optional = this.professorService.buscarPor(id);
        if (optional.isPresent()) {
            return ResponseEntity.ok(optional.get()); // retorna codigo 200
        } else {
            return ResponseEntity.notFound().build(); // retorna codigo 400
        }
    }

}
