package br.edu.utfpr.api1.dto;

import br.edu.utfpr.api1.model.Pessoa;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PessoaDTO(
    Long id,

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    String nome,

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    String email
) {

    // Converte de entidade para DTO
    public static PessoaDTO fromEntity(Pessoa pessoa) {
        return new PessoaDTO(
            pessoa.getId(),
            pessoa.getNome(),
            pessoa.getEmail()
        );
    }

    // Converte de DTO para entidade
    public Pessoa toEntity() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(this.id());
        pessoa.setNome(this.nome());
        pessoa.setEmail(this.email());
        return pessoa;
    }
}
