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

import br.edu.utfpr.api1.dto.PropriedadeDTO;
import br.edu.utfpr.api1.model.Propriedade;
import br.edu.utfpr.api1.repository.PropriedadeRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/propriedades")
@RequiredArgsConstructor
public class PropriedadeController {

    private final PropriedadeRepository propriedadeRepository;

    // [GET] Listar todas as propriedades
    @GetMapping
    public ResponseEntity<List<PropriedadeDTO>> getAll() {
        List<PropriedadeDTO> lista = propriedadeRepository.findAll().stream()
                .map(PropriedadeDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    // [GET] Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<PropriedadeDTO> getById(@PathVariable Long id) {
        return propriedadeRepository.findById(id)
                .map(PropriedadeDTO::fromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // [POST] Criar propriedade
    @PostMapping
    public ResponseEntity<PropriedadeDTO> create(@Valid @RequestBody PropriedadeDTO dto) {
        Propriedade entidade = dto.toEntity();
        Propriedade salva = propriedadeRepository.save(entidade);
        return ResponseEntity.status(201).body(PropriedadeDTO.fromEntity(salva));
    }

    // [PUT] Atualizar propriedade
    @PutMapping("/{id}")
    public ResponseEntity<PropriedadeDTO> update(@PathVariable Long id, @Valid @RequestBody PropriedadeDTO dto) {
        return propriedadeRepository.findById(id)
                .map(existing -> {
                    existing.setNome(dto.nome());
                    existing.setLocalizacao(dto.localizacao());
                    existing.setPoligono(dto.poligono());
                    Propriedade atualizada = propriedadeRepository.save(existing);
                    return ResponseEntity.ok(PropriedadeDTO.fromEntity(atualizada));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // [DELETE] Deletar propriedade
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return propriedadeRepository.findById(id)
                .map(p -> {
                    propriedadeRepository.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // [GET] Buscar apenas o polígono
    @GetMapping("/{id}/poligono")
    public ResponseEntity<List<String>> getPoligono(@PathVariable Long id) {
        return propriedadeRepository.findById(id)
                .map(propriedade -> ResponseEntity.ok(propriedade.getPoligono()))
                .orElse(ResponseEntity.notFound().build());
    }
}
