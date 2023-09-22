package com.pessoasapi.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
@Data
@ConfigurationProperties("pessoa-api.identificador")
public class IdentificadorProperties {

    private HashMap<Integer, String> documentos;

}
