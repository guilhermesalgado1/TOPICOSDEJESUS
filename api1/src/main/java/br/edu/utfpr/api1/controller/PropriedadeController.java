package br.edu.utfpr.api1.controller;

import br.edu.utfpr.api1.model.Propriedade;
import br.edu.utfpr.api1.repository.PropriedadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/propriedades")
@RequiredArgsConstructor
public class PropriedadeController {

    private final PropriedadeRepository propriedadeRepository;

    // Buscar todas as propriedades
    @GetMapping
    public ResponseEntity<List<Propriedade>> getAll() {
        return ResponseEntity.ok(propriedadeRepository.findAll());
    }

    // Buscar propriedade por ID
    @GetMapping("/{id}")
    public ResponseEntity<Propriedade> getById(@PathVariable Long id) {
        return propriedadeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Criar nova propriedade
    @PostMapping
    public ResponseEntity<Propriedade> create(@RequestBody Propriedade propriedade) {
        return ResponseEntity.ok(propriedadeRepository.save(propriedade));
    }

    // Atualizar propriedade existente
    @PutMapping("/{id}")
    public ResponseEntity<Propriedade> update(@PathVariable Long id, @RequestBody Propriedade propriedade) {
        return propriedadeRepository.findById(id)
                .map(existing -> {
                    propriedade.setId(id);
                    return ResponseEntity.ok(propriedadeRepository.save(propriedade));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Deletar propriedade por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return propriedadeRepository.findById(id)
                .map(p -> {
                    propriedadeRepository.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

 // Buscar apenas a lista de polígonos da propriedade
@GetMapping("/{id}/poligono")
public ResponseEntity<List<String>> getPoligono(@PathVariable Long id) {
    return propriedadeRepository.findById(id)
            .map(propriedade -> ResponseEntity.ok(propriedade.getPoligono()))
            .orElse(ResponseEntity.notFound().build());
}
}
