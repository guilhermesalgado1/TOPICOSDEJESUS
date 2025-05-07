package br.edu.utfpr.api1.dto;

import java.util.ArrayList;
import java.util.List;

import br.edu.utfpr.api1.model.GradeAmostral;
import br.edu.utfpr.api1.model.Propriedade;
import br.edu.utfpr.api1.model.TipoSolo;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record GradeAmostralDTO(
 
    Long id,

    @NotEmpty(message = "A lista de pontos não pode estar vazia")
    List<String> pontos,

    @NotNull(message = "O ID da propriedade é obrigatório")
    Long propriedadeId,

    @NotNull(message = "O ID do tipo de solo é obrigatório")
    Long tipoSoloId
) {

    public static GradeAmostralDTO fromEntity(GradeAmostral grade) {
        return new GradeAmostralDTO(
            grade.getId(),
            grade.getPontos() != null ? grade.getPontos() : new ArrayList<>(),
            grade.getPropriedade() != null ? grade.getPropriedade().getId() : null,
            grade.getTipoSolo() != null ? grade.getTipoSolo().getId() : null
        );
    }

    public GradeAmostral toEntity(Propriedade propriedade, TipoSolo tipoSolo) {
        GradeAmostral entity = new GradeAmostral();
        entity.setId(this.id());
        entity.setPontos(this.pontos());
        entity.setPropriedade(propriedade);
        entity.setTipoSolo(tipoSolo);
        return entity;
    }
}

