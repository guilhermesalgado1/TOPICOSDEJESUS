package br.edu.utfpr.api1.controller;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.utfpr.api1.dto.GradeAmostralDTO;
import br.edu.utfpr.api1.model.GradeAmostral;
import br.edu.utfpr.api1.model.Propriedade;
import br.edu.utfpr.api1.model.TipoSolo;
import br.edu.utfpr.api1.repository.GradeAmostralRepository;
import br.edu.utfpr.api1.repository.PropriedadeRepository;
import br.edu.utfpr.api1.repository.TipoSoloRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/grade-amostral")
@RequiredArgsConstructor
public class GradeAmostralController {

    private final GradeAmostralRepository gradeAmostralRepository;
    private final PropriedadeRepository propriedadeRepository;
    private final TipoSoloRepository tipoSoloRepository;

    // Buscar todas as grades amostrais com paginação
    @GetMapping
    public ResponseEntity<List<GradeAmostralDTO>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        var grades = gradeAmostralRepository.findAll(PageRequest.of(page, size))
                .stream().map(GradeAmostralDTO::fromEntity).toList();
        return ResponseEntity.ok(grades);
    }

    // Buscar grade amostral por ID
    @GetMapping("/{id}")
    public ResponseEntity<GradeAmostralDTO> getById(@PathVariable Long id) {
        return gradeAmostralRepository.findById(id)
                .map(GradeAmostralDTO::fromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Criar nova grade amostral
    @PostMapping
    public ResponseEntity<GradeAmostralDTO> create(@RequestBody GradeAmostralDTO dto) {
        Propriedade propriedade = propriedadeRepository.findById(dto.propriedadeId())
            .orElseThrow(() -> new RuntimeException("Propriedade não encontrada"));

        TipoSolo tipoSolo = tipoSoloRepository.findById(dto.tipoSoloId())
            .orElseThrow(() -> new RuntimeException("TipoSolo não encontrado"));

        GradeAmostral entity = dto.toEntity(propriedade, tipoSolo);
        entity = gradeAmostralRepository.save(entity);
        return ResponseEntity.ok(GradeAmostralDTO.fromEntity(entity));
    }

    // Atualizar grade amostral existente
    @PutMapping("/{id}")
    public ResponseEntity<GradeAmostralDTO> update(@PathVariable Long id, @RequestBody GradeAmostralDTO dto) {
        return gradeAmostralRepository.findById(id)
                .map(existing -> {
                    Propriedade propriedade = propriedadeRepository.findById(dto.propriedadeId())
                        .orElseThrow(() -> new RuntimeException("Propriedade não encontrada"));

                    TipoSolo tipoSolo = tipoSoloRepository.findById(dto.tipoSoloId())
                        .orElseThrow(() -> new RuntimeException("TipoSolo não encontrado"));

                    GradeAmostral entity = dto.toEntity(propriedade, tipoSolo);
                    entity.setId(id);

                    return ResponseEntity.ok(GradeAmostralDTO.fromEntity(gradeAmostralRepository.save(entity)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Deletar grade amostral por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return gradeAmostralRepository.findById(id)
                .map(existing -> {
                    gradeAmostralRepository.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Buscar apenas os pontos da grade amostral (json)
    @GetMapping("/{id}/pontos")
    public ResponseEntity<List<String>> getPontos(@PathVariable Long id) {
        return gradeAmostralRepository.findById(id)
                .map(GradeAmostral::getPontos)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
