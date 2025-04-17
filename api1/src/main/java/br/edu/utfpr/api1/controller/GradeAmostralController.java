package br.edu.utfpr.api1.controller;

import br.edu.utfpr.api1.model.GradeAmostral;
import br.edu.utfpr.api1.repository.GradeAmostralRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grade-amostral")
@RequiredArgsConstructor
public class GradeAmostralController {

    private final GradeAmostralRepository gradeAmostralRepository;

    // Buscar todas as grade amostrais com paginação
    @GetMapping
    public ResponseEntity<List<GradeAmostral>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(
            gradeAmostralRepository.findAll(PageRequest.of(page, size)).getContent()
        );
    }

    // Buscar grade amostral por ID
    @GetMapping("/{id}")
    public ResponseEntity<GradeAmostral> getById(@PathVariable Long id) {
        return gradeAmostralRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Criar nova grade amostral
    @PostMapping
    public ResponseEntity<GradeAmostral> create(@RequestBody GradeAmostral gradeAmostral) {
        return ResponseEntity.ok(gradeAmostralRepository.save(gradeAmostral));
    }

    // Atualizar grade amostral existente
    @PutMapping("/{id}")
    public ResponseEntity<GradeAmostral> update(@PathVariable Long id, @RequestBody GradeAmostral gradeAmostral) {
        return gradeAmostralRepository.findById(id)
                .map(existing -> {
                    gradeAmostral.setId(id);
                    return ResponseEntity.ok(gradeAmostralRepository.save(gradeAmostral));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Deletar grade amostral por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return gradeAmostralRepository.findById(id)
                .map(existing -> {
                    gradeAmostralRepository.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Buscar apenas os pontos da grade amostral (json)
    @GetMapping("/{id}/pontos")
    public ResponseEntity<List<String>> getPontos(@PathVariable Long id) {
        return gradeAmostralRepository.findById(id)
                .map(gradeAmostral -> ResponseEntity.ok(gradeAmostral.getPontos()))
                .orElse(ResponseEntity.notFound().build());
    }
}
