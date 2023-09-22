package com.pessoasapi.service;

import com.pessoasapi.dto.filter.PessoaFilter;
import com.pessoasapi.dto.request.PessoaRequest;
import com.pessoasapi.enums.TipoIdentificador;
import com.pessoasapi.exception.PessoaException;
import com.pessoasapi.model.Pessoa;
import com.pessoasapi.properties.IdentificadorProperties;
import com.pessoasapi.properties.TipoIdentificadorProperties;
import com.pessoasapi.properties.TipoIdentificadorValor;
import com.pessoasapi.repository.PessoaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PessoaServiceTest {

    @InjectMocks
    private PessoaService service;

    @Mock
    private PessoaRepository repository;
    @Mock
    private IdentificadorProperties identificadorProperties;
    @Mock
    private TipoIdentificadorProperties tipoIdentificadorProperties;

    private Pessoa pessoa;

    private PessoaRequest request;
    private HashMap<Integer, String> documentos;
    private HashMap<String, TipoIdentificadorValor> valores;

    @BeforeEach
    public void setup() {

        pessoa = Pessoa.builder()
                .id(1L)
                .nome("Teste").
                dataNascimento(LocalDate.now())
                .identificador("12345678912")
                .tipoIdentificador(TipoIdentificador.PF)
                .valorMaximo(100.0)
                .valorMinimo(10.0)
                .build();

        request = PessoaRequest
                .builder()
                .nome("Arthur")
                .identificador("12345678912")
                .dataNascimento(LocalDate.now())
                .build();

        documentos = new HashMap<>();
        documentos.put(11, "PF");
        valores = new HashMap<>();
        valores.put("PF", TipoIdentificadorValor.builder().valorMin(100.0).valorMax(1000.0).build());
    }

    @Test
    void deveListarTodosOsEnderecos() {

        when(repository.findAll(any(), any(PageRequest.class)))
                .thenReturn(new PageImpl<>(List.of(pessoa)));

        assertThat(service.findAll(PessoaFilter.builder().nome("Arthur").build(),
                PageRequest.of(0, 10)).getTotalElements(), equalTo(1L));
    }

    @Test
    void deveListarNenhumElemento() {

        when(repository.findAll(any(), any(PageRequest.class)))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

        assertThat(service.findAll(new PessoaFilter(),
                PageRequest.of(0, 10)).getTotalElements(), equalTo(0L));
    }

    @Test
    void deveAcharUmaPessoa() {
        when(repository.findById(anyLong()))
                .thenReturn(Optional.of(pessoa));
        assertThat(service.findById(1L).getId(), equalTo(1L));
    }

    @Test
    void deveLancarUmaExcecaoPorNaoEncontrarPessoaPorId() {
        when(repository.findById(anyLong())).
                thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.findById(1L));
    }

    @Test
    void deveDeletarUmRegistro() {
        service.delete(1L);
        verify(repository).deleteById(anyLong());
    }


    @Test
    void deveFalharAoTentarInserirUmRegistroNovo() {
        assertThrows(PessoaException.class, () -> service.save(request));
        verify(repository, never()).saveAndFlush(any(Pessoa.class));
    }

    @Test
    void deveCadastrarUmaPessoa() {
        when(identificadorProperties.getDocumentos()).thenReturn(documentos);
        when(tipoIdentificadorProperties.getValores()).thenReturn(valores);
        service.save(request);
        verify(repository).saveAndFlush(any(Pessoa.class));
    }

    @Test
    void deveAtualizarUmaPessoaExistente() {
        when(identificadorProperties.getDocumentos()).thenReturn(documentos);
        when(tipoIdentificadorProperties.getValores()).thenReturn(valores);
        when(repository.findById(anyLong()))
                .thenReturn(Optional.of(pessoa));
        service.update(1L, request);
        verify(repository).saveAndFlush(any(Pessoa.class));
    }


    @Test
    void deveLancarUmaExcecaoAoTentarAtualizarUmRegistroInexistente() {
        when(repository.findById(anyLong()))
                .thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.update(1L, request));
        verify(repository, never()).saveAndFlush(any(Pessoa.class));
    }
}