package br.edu.utfpr.api1.controller;

import br.edu.utfpr.api1.model.TipoSolo;
import br.edu.utfpr.api1.repository.TipoSoloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-solo")
@RequiredArgsConstructor
public class TipoSoloController {

    private final TipoSoloRepository tipoSoloRepository;

    @GetMapping
    public ResponseEntity<List<TipoSolo>> getAll() {
        return ResponseEntity.ok(tipoSoloRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoSolo> getById(@PathVariable Long id) {
        return tipoSoloRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TipoSolo> create(@RequestBody TipoSolo tipoSolo) {
        return ResponseEntity.ok(tipoSoloRepository.save(tipoSolo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoSolo> update(@PathVariable Long id, @RequestBody TipoSolo tipoSolo) {
        return tipoSoloRepository.findById(id)
                .map(existing -> {
                    tipoSolo.setId(id);
                    return ResponseEntity.ok(tipoSoloRepository.save(tipoSolo));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return tipoSoloRepository.findById(id)
                .map(tipo -> {
                    tipoSoloRepository.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Buscar apenas a descrição
    @GetMapping("/{id}/descricao")
    public ResponseEntity<String> getDescricao(@PathVariable Long id) {
        return tipoSoloRepository.findById(id)
                .map(tipo -> ResponseEntity.ok(tipo.getDescricao()))
                .orElse(ResponseEntity.notFound().build());
    }
}
