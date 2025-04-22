package br.edu.utfpr.api1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.utfpr.api1.model.Pessoa;
import br.edu.utfpr.api1.repository.PessoaRepository;



@RestController
@RequestMapping(value = "/pessoa", produces = "application/json")
public class PessoaController {

    @Autowired
    private PessoaRepository pessoaRepository;

    // [POST] Criar uma nova pessoa
    @PostMapping({"", "/"})
    public Pessoa createPessoa(@RequestBody Pessoa p) {
        return pessoaRepository.save(p);
    }

    // [GET] Obter todas as pessoas
    @GetMapping({"", "/"})
    public List<Pessoa> getAll() {
        return pessoaRepository.findAll();
    }

    // [GET] Obter pessoa por ID
    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> getById(@PathVariable Long id) {
        return pessoaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // [PUT] Atualizar pessoa por ID
    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> updatePessoa(@PathVariable Long id, @RequestBody Pessoa novaPessoa) {
        return pessoaRepository.findById(id)
                .map(p -> {
                    p.setNome(novaPessoa.getNome());
                    p.setEmail(novaPessoa.getEmail());
                    Pessoa atualizada = pessoaRepository.save(p);
                    return ResponseEntity.ok(atualizada);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // [DELETE] Deletar pessoa por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePessoa(@PathVariable Long id) {
        return pessoaRepository.findById(id)
                .map(p -> {
                    pessoaRepository.delete(p);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}

