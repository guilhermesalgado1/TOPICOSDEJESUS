package br.edu.utfpr.api1.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.utfpr.api1.dto.TipoSoloDTO;
import br.edu.utfpr.api1.model.TipoSolo;
import br.edu.utfpr.api1.repository.TipoSoloRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/tipos-solo", produces = "application/json")
@RequiredArgsConstructor
public class TipoSoloController {

    private final TipoSoloRepository tipoSoloRepository;

    // [GET] Buscar todos
    @GetMapping
    public ResponseEntity<List<TipoSoloDTO>> getAll() {
        List<TipoSoloDTO> lista = tipoSoloRepository.findAll().stream()
                .map(TipoSoloDTO::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }

    // [GET] Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<TipoSoloDTO> getById(@PathVariable Long id) {
        return tipoSoloRepository.findById(id)
                .map(TipoSoloDTO::fromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // [POST] Criar novo
    @PostMapping
    public ResponseEntity<TipoSoloDTO> create(@Valid @RequestBody TipoSoloDTO dto) {
        TipoSolo salvo = tipoSoloRepository.save(dto.toEntity());
        return ResponseEntity.status(201).body(TipoSoloDTO.fromEntity(salvo));
    }

    // [PUT] Atualizar existente
    @PutMapping("/{id}")
    public ResponseEntity<TipoSoloDTO> update(@PathVariable Long id, @Valid @RequestBody TipoSoloDTO dto) {
        return tipoSoloRepository.findById(id)
                .map(existing -> {
                    existing.setNome(dto.nome());
                    existing.setDescricao(dto.descricao());
                    TipoSolo atualizado = tipoSoloRepository.save(existing);
                    return ResponseEntity.ok(TipoSoloDTO.fromEntity(atualizado));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // [DELETE] Deletar por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return tipoSoloRepository.findById(id)
                .map(ts -> {
                    tipoSoloRepository.delete(ts);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // [GET] Buscar apenas a descrição
    @GetMapping("/{id}/descricao")
    public ResponseEntity<String> getDescricao(@PathVariable Long id) {
        return tipoSoloRepository.findById(id)
                .map(TipoSolo::getDescricao)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
