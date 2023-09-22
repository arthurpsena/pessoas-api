package com.pessoasapi.model;

import com.pessoasapi.enums.TipoIdentificador;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDate;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Pessoa {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(name = "NOME")
    private String nome;
    @Column(name = "DATANASCIMENTO")
    private LocalDate dataNascimento;
    @Enumerated(STRING)
    @Column(name = "TIPOIDENTIFICADOR")
    private TipoIdentificador tipoIdentificador;
    @Column(name = "IDENTIFICADOR", unique = true)
    private String identificador;
    @Column(name = "VALORMIN")
    private Double valorMinimo;
    @Column(name = "VALORMAX")
    private Double valorMaximo;

}
