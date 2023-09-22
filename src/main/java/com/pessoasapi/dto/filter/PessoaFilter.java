package com.pessoasapi.dto.filter;

import com.pessoasapi.model.Pessoa;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PessoaFilter {

    private String identificador;
    private String nome;

    public Pessoa toPessoa() {
        return Pessoa.builder()
                .identificador(identificador)
                .nome(nome)
                .build();
    }

}
