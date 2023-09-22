package com.pessoasapi.controller;

import com.pessoasapi.model.constants.Mappings;
import com.pessoasapi.dto.filter.PessoaFilter;
import com.pessoasapi.dto.request.PessoaRequest;
import com.pessoasapi.dto.response.PessoaResponse;
import com.pessoasapi.service.PessoaService;
import com.pessoasapi.controller.api.PessoaApi;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(Mappings.PATH_PESSOAS)
public class PessoaController implements PessoaApi {

    private final PessoaService service;

    @GetMapping("/{id}")
    public ResponseEntity<PessoaResponse> findById(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public Page<PessoaResponse> findAll(final PessoaFilter filter, @PageableDefault final Pageable pageable) {
        return service.findAll(filter, pageable);
    }

    @PostMapping
    public ResponseEntity<PessoaResponse> save(@Valid @RequestBody final PessoaRequest request) {
        return ResponseEntity.status(CREATED).body(service.save(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") final Long id) {
        service.delete(id);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") final Long id, @Valid @RequestBody final PessoaRequest request) {
        service.update(id, request);
        return ResponseEntity.status(NO_CONTENT).build();
    }

}
