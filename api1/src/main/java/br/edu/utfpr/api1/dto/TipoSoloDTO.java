package br.edu.utfpr.api1.dto;

import br.edu.utfpr.api1.model.TipoSolo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TipoSoloDTO(
    Long id,

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    String nome,

    @NotBlank(message = "Descrição é obrigatória")
    @Size(min = 5, message = "Descrição deve ter pelo menos 5 caracteres")
    String descricao
) {

    // Converte de entidade para DTO
    public static TipoSoloDTO fromEntity(TipoSolo tipoSolo) {
        return new TipoSoloDTO(
            tipoSolo.getId(),
            tipoSolo.getNome(),
            tipoSolo.getDescricao()
        );
    }

    // Converte de DTO para entidade
    public TipoSolo toEntity() {
        TipoSolo tipoSolo = new TipoSolo();
        tipoSolo.setId(this.id());
        tipoSolo.setNome(this.nome());
        tipoSolo.setDescricao(this.descricao());
        return tipoSolo;
    }
}
