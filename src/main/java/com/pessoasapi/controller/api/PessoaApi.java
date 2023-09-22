package com.pessoasapi.controller.api;

import com.pessoasapi.config.swagger.ApiPageable;
import com.pessoasapi.dto.filter.PessoaFilter;
import com.pessoasapi.dto.request.PessoaRequest;
import com.pessoasapi.dto.response.PessoaResponse;
import com.pessoasapi.dto.response.ErrorResponse;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Api(tags = "PersonApi")
@SwaggerDefinition(tags = { @Tag(name = "PersonApi", description = "Endpoints related to Person Api") })
public interface PessoaApi {

    @ApiOperation("Find an Person")
    @ApiResponses({ @ApiResponse(code = 200, message = "Pessoa encontrada"),
            @ApiResponse(code = 404, message = "Recurso nao encontrado"),
            @ApiResponse(code = 500, message = "Um erro inesperado aconteceu", response = ErrorResponse.class) })
    ResponseEntity<PessoaResponse> findById(@PathVariable("id") final Long id);

    @ApiOperation("Lista todas as pessoas")
    @ApiResponses({ @ApiResponse(code = 200, message = "Pessoas listadas"),
            @ApiResponse(code = 500, message = "Um erro inesperado aconteceu", response = ErrorResponse.class) })
    @ApiPageable
    Page<PessoaResponse> findAll(final PessoaFilter filter, @PageableDefault final Pageable pageable);

    @ApiOperation("Create an Person")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Pessoa criada com sucesso", response = PessoaResponse.class),
            @ApiResponse(code = 400, message = "Informacao invalida foi enviada no payload", response = ErrorResponse.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Um erro inesperado aconteceu", response = ErrorResponse.class) })
    ResponseEntity<PessoaResponse> save(@Valid @RequestBody final PessoaRequest request);

    @ApiOperation("Delete an Person")
    @ApiResponses(value = { @ApiResponse(code = 202, message = "Person disabled with success", response = void.class),
            @ApiResponse(code = 404, message = "Person not found", response = ErrorResponse.class) })
    ResponseEntity<Void> delete(@PathVariable("id") final Long id);

    @ApiOperation("Update an Person")
    @ApiResponses({ @ApiResponse(code = 202, message = "Pessoa atualizada com sucesso"),
            @ApiResponse(code = 404, message = "Recurso nao encontrado"),
            @ApiResponse(code = 400, message = "Informacao invalida foi enviada no payload", response = ErrorResponse.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Um erro inesperado aconteceu", response = ErrorResponse.class) })
    ResponseEntity<Void> update(@PathVariable("id") final Long id, @Valid @RequestBody final PessoaRequest request);

}
