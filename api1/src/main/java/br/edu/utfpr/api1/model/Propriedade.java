package br.edu.utfpr.api1.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_propriedade")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Propriedade extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String localizacao;

    /**
     * O polígono é uma lista de pontos que representam a área da propriedade.
     */
     @Column(columnDefinition = "jsonb",  name = "poligono")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> poligono = new ArrayList<>();


    @OneToMany(mappedBy = "propriedade", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GradeAmostral> gradeamostral;
}
