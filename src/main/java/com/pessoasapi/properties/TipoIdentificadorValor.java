package com.pessoasapi.properties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TipoIdentificadorValor {

    private Double valorMin;
    private Double valorMax;

}
