package br.edu.utfpr.api1.controller;

import java.util.List;
import java.util.stream.Collectors;

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

import br.edu.utfpr.api1.dto.PessoaDTO;
import br.edu.utfpr.api1.model.Pessoa;
import br.edu.utfpr.api1.repository.PessoaRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/pessoa", produces = "application/json")
public class PessoaController {

    @Autowired
    private PessoaRepository pessoaRepository;

    // [POST] Criar nova pessoa
    @PostMapping({"", "/"})
    public ResponseEntity<PessoaDTO> createPessoa(@RequestBody @Valid PessoaDTO dto) {
        Pessoa entity = dto.toEntity();
        Pessoa salvo = pessoaRepository.save(entity);
        return ResponseEntity.status(201).body(PessoaDTO.fromEntity(salvo));
    }

    // [GET] Obter todas as pessoas
    @GetMapping({"", "/"})
    public ResponseEntity<List<PessoaDTO>> getAll() {
        List<PessoaDTO> lista = pessoaRepository.findAll()
                .stream()
                .map(PessoaDTO::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }

    // [GET] Obter pessoa por ID
    @GetMapping("/{id}")
    public ResponseEntity<PessoaDTO> getById(@PathVariable Long id) {
        return pessoaRepository.findById(id)
                .map(PessoaDTO::fromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // [PUT] Atualizar pessoa por ID
    @PutMapping("/{id}")
    public ResponseEntity<PessoaDTO> updatePessoa(@PathVariable Long id, @RequestBody @Valid PessoaDTO dto) {
        return pessoaRepository.findById(id)
                .map(p -> {
                    p.setNome(dto.nome());
                    p.setEmail(dto.email());
                    Pessoa atualizado = pessoaRepository.save(p);
                    return ResponseEntity.ok(PessoaDTO.fromEntity(atualizado));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // [DELETE] Deletar pessoa por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePessoa(@PathVariable Long id) {
        return pessoaRepository.findById(id)
                .map(p -> {
                    pessoaRepository.delete(p);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
