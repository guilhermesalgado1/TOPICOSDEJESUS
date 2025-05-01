package br.edu.utfpr.api1.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.utfpr.api1.dto.GradeAmostralDTO;
import br.edu.utfpr.api1.model.GradeAmostral;
import br.edu.utfpr.api1.repository.GradeAmostralRepository;
import br.edu.utfpr.api1.repository.PropriedadeRepository;
import br.edu.utfpr.api1.repository.TipoSoloRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/gradeamostral")
@RequiredArgsConstructor
public class GradeAmostralController {

    private final GradeAmostralRepository gradeAmostralRepository;
    private final PropriedadeRepository propriedadeRepository;
    private final TipoSoloRepository tipoSoloRepository;

    // [GET] Obter todas as grades amostrais
    @GetMapping
    public ResponseEntity<List<GradeAmostral>> getAll() {
        return ResponseEntity.ok(gradeAmostralRepository.findAll());
    }

    // [GET] Obter grade amostral por ID
    @GetMapping("/{id}")
    public ResponseEntity<GradeAmostral> getById(@PathVariable Long id) {
        return gradeAmostralRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // [POST] Criar nova grade amostral
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody GradeAmostralDTO dto) {
        var propriedade = propriedadeRepository.findById(dto.propriedadeId()).orElse(null);
        var tipoSolo = tipoSoloRepository.findById(dto.tipoSoloId()).orElse(null);

        if (propriedade == null || tipoSolo == null) {
            return ResponseEntity.badRequest().body("Propriedade ou Tipo de Solo inválido.");
        }

        // Usando o método toEntity para criar a entidade a partir do DTO
        GradeAmostral entidade = dto.toEntity(propriedade, tipoSolo);

        // Salva e retorna a resposta
        return ResponseEntity.status(201).body(gradeAmostralRepository.save(entidade));
    }

    // [PUT] Atualizar grade amostral por ID
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody GradeAmostralDTO dto) {
        return gradeAmostralRepository.findById(id)
                .map(existing -> {
                    var propriedade = propriedadeRepository.findById(dto.propriedadeId()).orElse(null);
                    var tipoSolo = tipoSoloRepository.findById(dto.tipoSoloId()).orElse(null);

                    if (propriedade == null || tipoSolo == null) {
                        return ResponseEntity.badRequest().body("Propriedade ou Tipo de Solo inválido.");
                    }

                    // Usando o método toEntity para atualizar a entidade
                    GradeAmostral updatedEntity = dto.toEntity(propriedade, tipoSolo);
                    updatedEntity.setId(id);  // Mantém o ID da entidade para atualização

                    return ResponseEntity.ok(gradeAmostralRepository.save(updatedEntity));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // [DELETE] Deletar grade amostral por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return gradeAmostralRepository.findById(id)
                .map(p -> {
                    gradeAmostralRepository.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // [GET] Obter pontos da grade amostral
    @GetMapping("/{id}/pontos")
    public ResponseEntity<?> getPontos(@PathVariable Long id) {
        return gradeAmostralRepository.findById(id)
                .map(g -> ResponseEntity.ok(g.getPontos()))
                .orElse(ResponseEntity.notFound().build());
    }
}
