package br.edu.utfpr.api1.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_grade_amostral")
@NoArgsConstructor
@AllArgsConstructor
public class GradeAmostral extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "grade_amostral_pontos", joinColumns = @JoinColumn(name = "grade_amostral_id"))
    @Column(name = "coordenada")
    private List<String> pontos = new ArrayList<>();

   // @OneToMany( cascade = CascadeType.ALL, orphanRemoval = true)
   // lista de pontos

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @ManyToOne
    @JoinColumn(name = "propriedade_id")
    private Propriedade propriedade;

    @ManyToOne
    @JoinColumn(name = "tipo_solo_id")
    private TipoSolo tipoSolo;
}
