package br.edu.utfpr.api1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="tb_pessoa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data

public class Pessoa extends BaseEntity{
    @Column (name = "nome",nullable = false, length = 100)
    private String nome;
    @Column (name = "email",nullable = false, length = 150)
    private String email;
}
