package com.ifma.vinculotcc_api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

import com.ifma.vinculotcc_api.controllers.AlunoController;

@SpringBootTest
public class AlunoControllerTest {
    @Autowired
    private AlunoController alunoController; 

    @Test
    public void contextLoads() throws Exception{
        assertThat(alunoController).isNotNull();
    }


}
