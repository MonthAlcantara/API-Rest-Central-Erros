package br.com.monthalcantara.projetofinal.endpoint;

import br.com.monthalcantara.projetofinal.dto.UsuarioDTO;
import br.com.monthalcantara.projetofinal.entity.Usuario;
import br.com.monthalcantara.projetofinal.service.interfaces.UsuarioService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    UsuarioService userService;

    @Autowired
    private RestTemplate restTemplate;

    @ApiResponses(	value = {
            @ApiResponse(code = 200, message = "Token criado com sucesso", response = String.class),
            @ApiResponse(code = 204, message = "Login Invalid ! Usuario nao encontrado ", response = Error.class),
            @ApiResponse(code = 500, message = "Password Invalido!", response = Error.class)	})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value="gerar Token")
    @GetMapping("/gerarToken")
    public ResponseEntity<String> gerarToken(@RequestParam String login, @RequestParam String  password) throws NotFoundException {

        Optional<Usuario> user = userService.findByLogin(login);
        if(user.isPresent()) {
            return new ResponseEntity<>(restTemplate.postForObject("http://localhost:8080/login",
                    "{\n" +
                            "	\"login\":\"" + login + "\",\n" +
                            "	\"password\":\"" + password + "\"\n" +
                            "}", String.class), HttpStatus.OK);

        }
        else  throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Usuario não encontrado");
    }

    @GetMapping
    @ApiOperation("Busca todos os Usuários")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Usuarios não localizados"),
            @ApiResponse(code = 200, message = "Usuarios localizados")})
    public ResponseEntity<List<Usuario>> findAll() {
        return new ResponseEntity<>(this.userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation("Busca um usuário pelo Id")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Usuario não localizado"),
            @ApiResponse(code = 200, message = "Usuario localizado")})
    public ResponseEntity<Usuario> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(this.userService.findById(id).get(), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation("Cria um novo Usuário")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Não foi possível criar o Usuario"),
            @ApiResponse(code = 200, message = "Usuario criado"),
            @ApiResponse(code = 201, message = "Usuario criado com sucesso")})
    public ResponseEntity<UsuarioDTO> create(@Valid @RequestBody Usuario usuario) {
        return new ResponseEntity<>(new UsuarioDTO(this.userService.save(usuario)), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Exclui um Usuário")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Usuario não localizado"),
            @ApiResponse(code = 200, message = "Usuario localizado"),
            @ApiResponse(code = 201, message = "Usuario deletado com sucesso")})
    public void deleteById(@PathVariable("id") Long id) {
        this.userService.deleteById(id);
    }


    @PutMapping("/{id}")
    @ApiOperation("Atualiza um Usuário")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Usuario não localizado"),
            @ApiResponse(code = 200, message = "Usuario localizado"),
            @ApiResponse(code = 201, message = "Usuario atualizado com sucesso")})
    public ResponseEntity<UsuarioDTO> update(@PathVariable(value="id") Long id ,@Valid @RequestBody Usuario user )  {
        return new ResponseEntity<>(new UsuarioDTO(this.userService.updateUsuario(id, user)), HttpStatus.ACCEPTED);

    }

}
