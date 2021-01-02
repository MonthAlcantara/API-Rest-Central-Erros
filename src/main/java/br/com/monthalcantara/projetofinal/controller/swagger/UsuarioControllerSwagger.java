package br.com.monthalcantara.projetofinal.controller.swagger;

import br.com.monthalcantara.projetofinal.dto.UsuarioDTO;
import br.com.monthalcantara.projetofinal.model.Usuario;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;


public interface UsuarioControllerSwagger {


    @GetMapping("/protected")
    @ApiOperation("Busca todos os Usuários")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Usuários não localizados"),
            @ApiResponse(code = 403, message = "Você não possui permissão para visualizar este recurso"),
            @ApiResponse(code = 401, message = "Você não possui credenciais de autenticação válidas"),
            @ApiResponse(code = 200, message = "Usuários localizados")})
    public ResponseEntity findAll(Pageable pageable);


    @GetMapping("/protected/{id}")
    @ApiOperation("Busca um usuário pelo Id")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Usuário não localizado"),
            @ApiResponse(code = 403, message = "Você não possui permissão para visualizar este recurso"),
            @ApiResponse(code = 401, message = "Você não possui credenciais de autenticação válidas"),
            @ApiResponse(code = 200, message = "Usuário localizado")})
    public ResponseEntity findById(@PathVariable("id") Long id);

    @GetMapping("protected/login/{login}")
    @ApiOperation("Busca um usuário pelo Login")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Usuário não localizado"),
            @ApiResponse(code = 403, message = "Você não possui permissão para visualizar este recurso"),
            @ApiResponse(code = 401, message = "Você não possui credenciais de autenticação válidas"),
            @ApiResponse(code = 200, message = "Usuário localizado")})
    public ResponseEntity byLogin(@PathVariable("login") String login);


    @PostMapping
    @ApiOperation("Cria um novo Usuário")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Não foi possível criar o Usuário"),
            @ApiResponse(code = 403, message = "Você não possui permissão para visualizar este recurso"),
            @ApiResponse(code = 401, message = "Você não possui credenciais de autenticação válidas"),
            @ApiResponse(code = 200, message = "Usuário criado"),
            @ApiResponse(code = 201, message = "Usuário criado com sucesso")})
    public ResponseEntity save(@Valid @RequestBody UsuarioDTO usuarioDTO, UriComponentsBuilder builder);

    @DeleteMapping("/admin/{id}")
    @ApiOperation("Exclui um Usuário")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Usuário não localizado"),
            @ApiResponse(code = 403, message = "Você não possui permissão para visualizar este recurso"),
            @ApiResponse(code = 401, message = "Você não possui credenciais de autenticação válidas"),
            @ApiResponse(code = 200, message = "Usuário localizado"),
            @ApiResponse(code = 201, message = "Usuário excluído com sucesso")})
    public void deleteById(@PathVariable("id") Long id);


    @PutMapping("/admin/{id}")
    @ApiOperation("Atualiza um Usuário")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Usuário não localizado"),
            @ApiResponse(code = 403, message = "Você não possui permissão para visualizar este recurso"),
            @ApiResponse(code = 401, message = "Você não possui credenciais de autenticação válidas"),
            @ApiResponse(code = 200, message = "Usuário localizado"),
            @ApiResponse(code = 201, message = "Usuário atualizado com sucesso")})
    public ResponseEntity update(@PathVariable(value = "id") Long id, @Valid @RequestBody Usuario user);
}
