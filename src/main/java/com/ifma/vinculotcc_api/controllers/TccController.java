package com.ifma.vinculotcc_api.controllers;

import java.util.Optional;
import java.util.Set;

import com.ifma.vinculotcc_api.models.Aluno;
import com.ifma.vinculotcc_api.models.Professor;
import com.ifma.vinculotcc_api.models.Tcc;
import com.ifma.vinculotcc_api.models.enums.StatusTCC;
import com.ifma.vinculotcc_api.services.AlunoService;
import com.ifma.vinculotcc_api.services.ProfessorService;
import com.ifma.vinculotcc_api.services.TccService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/tccs")
public class TccController {
    @Autowired
    private TccService tccService;
    @Autowired
    private ProfessorService profService;
    @Autowired
    private AlunoService alunoService;

    private Long idAlunoTcc;

    @RequestMapping("/{id_aluno}/{id_professor}/novo") 
    public ModelAndView novoTccSolicitado(@PathVariable("id_aluno") Long id_aluno,
    @PathVariable("id_professor") Long id_professor) {
        ModelAndView mv = new ModelAndView("CadastroTcc");
        
        this.setIdAlunoTcc(id_aluno);

        Optional<Aluno> optional_al = alunoService.buscarPor(id_aluno);
        Optional<Professor> optional_prof = profService.buscarPor(id_professor);
            
        mv.addObject("aluno", optional_al.get());
        mv.addObject("prof", optional_prof.get());    
        
        return mv;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView cadastrar(Tcc tcc) {
        ModelAndView mv = new ModelAndView("solicitado");
        if(tccService.validar_alunoTcc(alunoService, this.idAlunoTcc)) {
            tcc.setStatus(StatusTCC.PENDENTE);
            this.tccService.salva(tcc);
            mv.addObject("id", idAlunoTcc);
            mv.addObject("mensagem", "Sua solicitação foi enviada");
        } else {
            mv.addObject("id", idAlunoTcc);
            mv.addObject("mensagem", "Você já enviou solicitação para esse professor");
        }

        return mv;
        
        //return "redirect:professores/pesquisar/"+idAlunoTcc;//http://localhost:8080/professores/pesquisar/1
        //return "redirect:alunos/autentica-aluno";
    }

    @RequestMapping("{codigo}/{aceito}/{id_professor}") 
    public ModelAndView atualizarStatusTcc(@PathVariable("codigo") Long id_Tcc, @PathVariable("aceito") int aceito, @PathVariable("id_professor") int id_professor) {
        Tcc tcc = tccService.buscarUm(id_Tcc);
        
        if(aceito == 1) {
            tcc.setStatus(StatusTCC.ACEITO);
            tccService.salva(tcc);
        } else {
            tcc.setStatus(StatusTCC.REJEITADO);
            tccService.salva(tcc);
        }
        
        Set<Tcc> tccs = tcc.getProfessor().getTccs();
        ModelAndView mv = new ModelAndView("Solicitacoes");
        mv.addObject("solicitacoes", tccs);
        return mv;
    }

    public Long getIdAlunoTcc() {
        return idAlunoTcc;
    }

    public void setIdAlunoTcc(Long idAlunoTcc) {
        this.idAlunoTcc = idAlunoTcc;
    }

    public ResponseEntity<Tcc> buscaPor(@PathVariable Long id) {
        Optional<Tcc> optional = this.tccService.buscarPor(id);
        if (optional.isPresent()) {
            return ResponseEntity.ok(optional.get()); // retorna codigo 200
        } else {
            return ResponseEntity.notFound().build(); // retorna codigo 400
        }
    }

    
}
