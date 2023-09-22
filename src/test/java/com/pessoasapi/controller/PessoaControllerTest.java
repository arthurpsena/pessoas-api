package com.pessoasapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pessoasapi.dto.request.PessoaRequest;
import com.pessoasapi.dto.response.PessoaResponse;
import com.pessoasapi.enums.TipoIdentificador;
import com.pessoasapi.model.Pessoa;
import com.pessoasapi.service.PessoaService;
import com.pessoasapi.exception.PessoaException;
import com.pessoasapi.exception.NotFoundException;
import com.pessoasapi.exception.handler.PessoaExceptionHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PessoaController.class)
@ContextConfiguration(classes = {PessoaController.class, PessoaExceptionHandler.class})
class PessoaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PessoaService service;

    private PessoaResponse pessoaResponse;

    @Autowired
    private ObjectMapper mapper;

    private PessoaRequest request;

    @BeforeEach
    void setUp() {

        pessoaResponse = new PessoaResponse(Pessoa.builder()
                .nome("Teste").
                dataNascimento(LocalDate.now())
                .identificador("12345678912")
                .tipoIdentificador(TipoIdentificador.PF)
                .valorMaximo(100.0)
                .valorMinimo(10.0)
                .build());

        request = PessoaRequest
                .builder()
                .identificador("12345678912")
                .nome("Arthur")
                .dataNascimento(LocalDate.now())
                .build();

    }

    @Test
    void deveListarAsPessoas() throws Exception {
        when(service.findAll(any(), any())).thenReturn(new PageImpl<>(List.of(pessoaResponse)));
        mockMvc.perform(
                get("/pessoas").content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void deveAcharUmaPessoa() throws Exception {
        when(service.findById(anyLong())).thenReturn(pessoaResponse);
        mockMvc.perform(
                get("/pessoas/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void deveLancarStatusCode_404() throws Exception {
        when(service.findById(anyLong())).thenThrow(NotFoundException.class);
        mockMvc.perform(
                get("/pessoas/98")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    void deveValidarSePessoaEstaDevidamentePreenchida() throws Exception {
        request.setNome("");
        mockMvc.perform(
                post("/pessoas").content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isBadRequest());
        verify(service, never()).save(any());
    }

    @Test
    void deveSalvarUmaNovaPessoa() throws Exception {
        when(service.save(any())).thenReturn(pessoaResponse);
        mockMvc.perform(
                post("/pessoas").content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isCreated());
    }

    @Test
    void deveAtualizarUmaPessoa() throws Exception {
        when(service.update(anyLong(), any())).thenReturn(pessoaResponse);
        mockMvc.perform(
                put("/pessoas/1").content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    void deveDeletarUmaPessoa() throws Exception {
        mockMvc.perform(
                delete("/pessoas/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    void deveLancarStatusCode_500() throws Exception {
        when(service.save(any())).thenThrow(PessoaException.class);
        mockMvc.perform(
                post("/pessoas").content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isInternalServerError());
    }
}