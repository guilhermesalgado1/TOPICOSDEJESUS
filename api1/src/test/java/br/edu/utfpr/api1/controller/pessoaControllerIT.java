package br.edu.utfpr.api1.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

// Ajuste import para PessoaDTO
import br.edu.utfpr.api1.dto.PessoaDTO;

@SpringBootTest
@AutoConfigureMockMvc
public class pessoaControllerIT {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void deveCriarPessoa() throws Exception {
        var novo = new PessoaDTO(null, "Geringoncelos", "geringoncelos@email.com");
        mvc.perform(post("/pessoa")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(novo)))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id", notNullValue()))
           .andExpect(jsonPath("$.nome").value("Geringoncelos"));
    }

    @Test
    void deveListarTodasAsPessoas() throws Exception {
        // Criar duas pessoas antes do GET
        var pessoa1 = new PessoaDTO(null, "Joana", "joana@email.com");
        var pessoa2 = new PessoaDTO(null, "Carlos", "carlos@email.com");
    
        mvc.perform(post("/pessoa")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(pessoa1)))
           .andExpect(status().isOk());
    
        mvc.perform(post("/pessoa")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(pessoa2)))
           .andExpect(status().isOk());
    
        // Agora faz o GET all
        mvc.perform(get("/pessoa"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
           .andExpect(jsonPath("$[*].nome", hasItems("Joana", "Carlos")));
    }

    @Test
    void deveCriarEBuscarPessoaPorId() throws Exception {
        var novo = new PessoaDTO(null, "Geringoncelos", "geringoncelos@email.com");
        mvc.perform(post("/pessoa")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(novo)))
           .andExpect(status().isOk());

        mvc.perform(get("/pessoa/1"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.nome").value("Geringoncelos"));
    }

    @Test
    void deveCriarEAtualizarPessoaPorId() throws Exception {
        var novo = new PessoaDTO(null, "Geringoncelos", "geringoncelos@email.com");
        mvc.perform(post("/pessoa")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(novo)))
           .andExpect(status().isOk());

        var atualizada = new PessoaDTO(null, "Humunculus", "huminculus@email.com");
        mvc.perform(put("/pessoa/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(atualizada)))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.nome").value("Humunculus"));
    }

    @Test
    void deveCriarEDeletarPessoaPorId() throws Exception {
        var novo = new PessoaDTO(null, "Geringoncelos", "geringoncelos@email.com");
        mvc.perform(post("/pessoa")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(novo)))
           .andExpect(status().isOk());

        mvc.perform(delete("/pessoa/1"))
           .andExpect(status().isNoContent());
        
        mvc.perform(get("/pessoa/1"))
           .andExpect(status().isNotFound());
    }
}
