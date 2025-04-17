package br.edu.utfpr.api1.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_grade_amostral")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GradeAmostral extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //nome

    //@ElementCollection
    //@CollectionTable(name = "tb_grade_amostral_pontos", joinColumns = @JoinColumn(name = "grade_amostral_id"))
    // Pontos amostrais
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
