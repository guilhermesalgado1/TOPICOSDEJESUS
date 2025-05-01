package br.edu.utfpr.api1.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_grade_amostral")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GradeAmostral extends BaseEntity {

    @Column(columnDefinition = "jsonb",  name = "pontos")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> pontos = new ArrayList<>();


    @ManyToOne
    @JoinColumn(name = "propriedade_id")
    private Propriedade propriedade;

    @ManyToOne
    @JoinColumn(name = "tipo_solo_id")
    private TipoSolo tipoSolo;


}
