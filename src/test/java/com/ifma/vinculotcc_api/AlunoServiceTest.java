package com.ifma.vinculotcc_api;

import com.ifma.vinculotcc_api.models.Aluno;
import com.ifma.vinculotcc_api.models.enums.PeriodoAluno;
import com.ifma.vinculotcc_api.services.AlunoService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AlunoServiceTest {
    /* O tcc deve ser null quando cadastrar um novo aluno no sistema. Isso 
    vai retornar um erro. */
    @Autowired
    AlunoService alunoService;

    @Test
    public void tccDeveSerNull_QuandoCadastrarUmNovoAluno() {
        assertThat( 
            this.alunoService.salva(new Aluno(PeriodoAluno.OITAVO)).getTcc()
        ).isNull();
    }
}
