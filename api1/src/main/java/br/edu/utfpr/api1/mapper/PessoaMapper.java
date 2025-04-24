package br.edu.utfpr.api1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.edu.utfpr.api1.dto.PessoaDTO;
import br.edu.utfpr.api1.model.Pessoa;

@Mapper(componentModel = "spring")
public interface PessoaMapper {
    PessoaDTO toDTO(Pessoa entidade);

    @Mapping(target = "dataCriacao", expression = "java(java.time.Instant.now())")
    Pessoa toEntity(PessoaDTO dto);
}