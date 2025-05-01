package br.edu.utfpr.api1.dto;

import java.util.ArrayList;
import java.util.List;

import br.edu.utfpr.api1.model.Propriedade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record PropriedadeDTO(
    Long id,

    @NotBlank(message = "Nome é obrigatório")
    String nome,

    @NotBlank(message = "Localização é obrigatória")
    String localizacao,

    @NotEmpty(message = "O polígono não pode estar vazio")
    List<@NotBlank(message = "Cada ponto do polígono deve ser uma string não vazia") String> poligono
) {

    public static PropriedadeDTO fromEntity(Propriedade propriedade) {
        return new PropriedadeDTO(
            propriedade.getId(),
            propriedade.getNome(),
            propriedade.getLocalizacao(),
            propriedade.getPoligono() != null ? propriedade.getPoligono() : new ArrayList<>()
        );
    }

    public Propriedade toEntity() {
        Propriedade entidade = new Propriedade();
        entidade.setId(this.id());
        entidade.setNome(this.nome());
        entidade.setLocalizacao(this.localizacao());
        entidade.setPoligono(this.poligono());
        return entidade;
    }
}
