package br.com.monthalcantara.projetofinal.endpoint;

import br.com.monthalcantara.projetofinal.dto.UsuarioDTO;
import br.com.monthalcantara.projetofinal.entity.Usuario;
import br.com.monthalcantara.projetofinal.service.interfaces.UsuarioService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/usuarios")
public class UsuarioController {
    @Autowired
    UsuarioService userService;


    @GetMapping("/protected")
    @ApiOperation("Busca todos os Usuários")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Usuarios não localizados"),
            @ApiResponse(code = 403, message = "Você não possui permissão para visualizar este recurso"),
            @ApiResponse(code = 401, message = "Você não possui credenciais de autenticação válidas"),
            @ApiResponse(code = 200, message = "Usuarios localizados")})
    public ResponseEntity findAll(Pageable pageable) {
        return new ResponseEntity(this.userService.findAll(pageable), HttpStatus.OK);
    }


    @GetMapping("/protected/{id}")
    @ApiOperation("Busca um usuário pelo Id")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Usuario não localizado"),
            @ApiResponse(code = 403, message = "Você não possui permissão para visualizar este recurso"),
            @ApiResponse(code = 401, message = "Você não possui credenciais de autenticação válidas"),
            @ApiResponse(code = 200, message = "Usuario localizado")})
    public ResponseEntity findById(@PathVariable("id") Long id) {
        return new ResponseEntity(this.userService.findById(id), HttpStatus.OK);
    }

    @GetMapping("protected/login/{login}")
    @ApiOperation("Busca um usuário pelo Login")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Usuario não localizado"),
            @ApiResponse(code = 403, message = "Você não possui permissão para visualizar este recurso"),
            @ApiResponse(code = 401, message = "Você não possui credenciais de autenticação válidas"),
            @ApiResponse(code = 200, message = "Usuario localizado")})
    public ResponseEntity byLogin(@PathVariable("login") String login) {
        return new ResponseEntity(this.userService.findByLogin(login), HttpStatus.OK);
    }


    @PostMapping
    @ApiOperation("Cria um novo Usuário")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Não foi possível criar o Usuario"),
            @ApiResponse(code = 403, message = "Você não possui permissão para visualizar este recurso"),
            @ApiResponse(code = 401, message = "Você não possui credenciais de autenticação válidas"),
            @ApiResponse(code = 200, message = "Usuario criado"),
            @ApiResponse(code = 201, message = "Usuario criado com sucesso")})
    public ResponseEntity create(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        return new ResponseEntity(new UsuarioDTO(this.userService.save(usuarioDTO.build())), HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/{id}")
    @ApiOperation("Exclui um Usuário")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Usuario não localizado"),
            @ApiResponse(code = 403, message = "Você não possui permissão para visualizar este recurso"),
            @ApiResponse(code = 401, message = "Você não possui credenciais de autenticação válidas"),
            @ApiResponse(code = 200, message = "Usuario localizado"),
            @ApiResponse(code = 201, message = "Usuario deletado com sucesso")})
    public void deleteById(@PathVariable("id") Long id) {
        this.userService.deleteById(id);
    }


    @PutMapping("/admin/{id}")
    @ApiOperation("Atualiza um Usuário")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Usuario não localizado"),
            @ApiResponse(code = 403, message = "Você não possui permissão para visualizar este recurso"),
            @ApiResponse(code = 401, message = "Você não possui credenciais de autenticação válidas"),
            @ApiResponse(code = 200, message = "Usuario localizado"),
            @ApiResponse(code = 201, message = "Usuario atualizado com sucesso")})
    public ResponseEntity update(@PathVariable(value = "id") Long id, @Valid @RequestBody Usuario user) {
        return new ResponseEntity(new UsuarioDTO(this.userService.updateUsuario(id, user)), HttpStatus.NO_CONTENT);

    }

}
