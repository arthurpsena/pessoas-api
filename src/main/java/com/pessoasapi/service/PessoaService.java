package com.pessoasapi.service;

import com.pessoasapi.dto.filter.PessoaFilter;
import com.pessoasapi.dto.request.PessoaRequest;
import com.pessoasapi.dto.response.PessoaResponse;
import com.pessoasapi.enums.TipoIdentificador;
import com.pessoasapi.exception.NotFoundException;
import com.pessoasapi.exception.PessoaException;
import com.pessoasapi.model.Pessoa;
import com.pessoasapi.properties.IdentificadorProperties;
import com.pessoasapi.properties.TipoIdentificadorProperties;
import com.pessoasapi.properties.TipoIdentificadorValor;
import com.pessoasapi.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static org.springframework.data.domain.ExampleMatcher.StringMatcher.CONTAINING;

@Service
@RequiredArgsConstructor
public class PessoaService {

    private final PessoaRepository repository;
    private final IdentificadorProperties identificadorProperties;
    private final TipoIdentificadorProperties tipoIdentificadorProperties;

    public Page<PessoaResponse> findAll(final PessoaFilter filter, final Pageable pageable) {
        final Example<Pessoa> example = Example.of(filter.toPessoa(),
                ExampleMatcher.matchingAll().withStringMatcher(CONTAINING).withIgnoreCase());
        return repository.findAll(example,pageable).map(PessoaResponse::new);
    }

    public PessoaResponse findById(final Long id) {
        return repository.findById(id)
                .map(PessoaResponse::new)
                .orElseThrow(NotFoundException::new);
    }

    public void delete(final Long id) {
        repository.deleteById(id);
    }

    public PessoaResponse save(final PessoaRequest request) {
        return new PessoaResponse(repository.saveAndFlush(createPessoa(request)));
    }

    public PessoaResponse update(final Long id, final PessoaRequest request) {
        final Pessoa found = repository.findById(id).orElseThrow(NotFoundException::new);
        final Pessoa pessoaToUpdate = createPessoa(request);
        pessoaToUpdate.setId(id);
        BeanUtils.copyProperties(pessoaToUpdate, found);
        return new PessoaResponse(repository.saveAndFlush(found));
    }

    private Pessoa createPessoa(final PessoaRequest request){
        final String tipoDocumento = identificadorProperties.getDocumentos().getOrDefault(request.getIdentificador().length(), "");
        final TipoIdentificadorValor tipoIdentificadorValor = tipoIdentificadorProperties.getValores().getOrDefault(
                tipoDocumento,
                null);
        if(tipoIdentificadorValor == null){
            throw new PessoaException();
        }

        return Pessoa.builder().nome(request.getNome())
                .dataNascimento(request.getDataNascimento())
                .identificador(request.getIdentificador())
                .tipoIdentificador(TipoIdentificador.valueOf(tipoDocumento))
                .valorMaximo(tipoIdentificadorValor.getValorMax())
                .valorMinimo(tipoIdentificadorValor.getValorMin()).build();
    }

}
