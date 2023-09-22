package com.pessoasapi.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pessoasapi.enums.TipoIdentificador;
import com.pessoasapi.model.Pessoa;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "Pessoa Response")
public class PessoaResponse {

    private final Pessoa pessoa;

    @ApiModelProperty(value = "ID")
    public Long getId() {
        return pessoa.getId();
    }

    @ApiModelProperty(value = "Nome")
    public String getNome() {
        return pessoa.getNome();
    }

    @ApiModelProperty(value = "Data de Nascimento")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    public LocalDate getDataNascimento() {
        return pessoa.getDataNascimento();
    }

    @ApiModelProperty(value = "Identificador")
    public String getIdentificador() {
        return pessoa.getIdentificador();
    }

    @ApiModelProperty(value = "Tipo de Identificador")
    public TipoIdentificador tTipoIdentificador() {
        return pessoa.getTipoIdentificador();
    }

    @ApiModelProperty(value = "Valor Minimo")
    public Double getValorMinimo() {
        return pessoa.getValorMinimo();
    }

    @ApiModelProperty(value = "Valor Maximo")
    public Double getValorMaximo() {
        return pessoa.getValorMaximo();
    }


}
