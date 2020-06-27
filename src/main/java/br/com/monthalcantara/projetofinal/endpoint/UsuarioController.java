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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/usuarios")
public class UsuarioController {
    @Autowired
    UsuarioService userService;

    @Autowired
    RestTemplate restTemplate;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Token criado com sucesso", response = String.class),
            @ApiResponse(code = 204, message = "Login Invalid ! Usuario nao encontrado ", response = Error.class),
            @ApiResponse(code = 500, message = "Password Invalido!", response = Error.class)})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "gerar Token")
    @GetMapping("/gerarToken")
    public ResponseEntity<String> gerarToken(@RequestParam String login, @RequestParam String password) {
        Optional<Usuario> user = Optional.ofNullable(userService.getUserInfoByUsuarioLogin(login));
        if (user.isPresent()) {
            return new ResponseEntity<>(restTemplate.postForObject("http://localhost:8080/login",
                    "{\n" +
                            "	\"login\":\"" + login + "\",\n" +
                            "	\"password\":\"" + password + "\"\n" +
                            "}", String.class), HttpStatus.OK);
        } else throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Usuario não encontrado");
    }

    @GetMapping("/protected")
    @ApiOperation("Busca todos os Usuários")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Usuarios não localizados"),
            @ApiResponse(code = 200, message = "Usuarios localizados")})
    public ResponseEntity<List<UsuarioDTO>> findAll(Pageable pageable) {
        return new ResponseEntity<>(this.userService.findAll(pageable), HttpStatus.OK);
    }



    @GetMapping("/protected/{id}")
    @ApiOperation("Busca um usuário pelo Id")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Usuario não localizado"),
            @ApiResponse(code = 200, message = "Usuario localizado")})
    public ResponseEntity<UsuarioDTO> findById(@PathVariable("id") Long id) {
        UsuarioDTO usuarioDTO = this.userService.findById(id);
        if (usuarioDTO == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);
    }

    @GetMapping("protected/login/{login}")
    @ApiOperation("Busca um usuário pelo Login")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Usuario não localizado"),
            @ApiResponse(code = 200, message = "Usuario localizado")})
    public ResponseEntity<UsuarioDTO> byLogin(@PathVariable("login") String login) {
        UsuarioDTO usuarioDTO = this.userService.findByLogin(login);
        if (usuarioDTO == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);
    }


    @PostMapping("/save")
    @ApiOperation("Cria um novo Usuário")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Não foi possível criar o Usuario"),
            @ApiResponse(code = 200, message = "Usuario criado"),
            @ApiResponse(code = 201, message = "Usuario criado com sucesso")})
    public ResponseEntity<UsuarioDTO> create(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        return new ResponseEntity<>(new UsuarioDTO(this.userService.save(usuarioDTO.build())), HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/{id}")
    @ApiOperation("Exclui um Usuário")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Usuario não localizado"),
            @ApiResponse(code = 200, message = "Usuario localizado"),
            @ApiResponse(code = 201, message = "Usuario deletado com sucesso")})
    public void deleteById(@PathVariable("id") Long id) {
        this.userService.deleteById(id);
    }


    @PutMapping("/admin/{id}")
    @ApiOperation("Atualiza um Usuário")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Usuario não localizado"),
            @ApiResponse(code = 200, message = "Usuario localizado"),
            @ApiResponse(code = 201, message = "Usuario atualizado com sucesso")})
    public ResponseEntity<UsuarioDTO> update(@PathVariable(value = "id") Long id, @Valid @RequestBody Usuario user) {
        return new ResponseEntity<>(new UsuarioDTO(this.userService.updateUsuario(id, user)), HttpStatus.ACCEPTED);

    }

}
