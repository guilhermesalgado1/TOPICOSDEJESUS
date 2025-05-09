package br.edu.utfpr.api1.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Table(name = "tb_tipos_solo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoSolo extends BaseEntity {
    
    private String nome;

    private String descricao;

    @OneToMany(mappedBy = "tipoSolo")
    @JsonIgnore
    private List<GradeAmostral> gradeamostral;
}
