package br.com.monthalcantara.projetofinal.controller;

import br.com.monthalcantara.projetofinal.dto.CredenciaisDTO;
import br.com.monthalcantara.projetofinal.dto.TokenDTO;
import br.com.monthalcantara.projetofinal.dto.UsuarioDTO;
import br.com.monthalcantara.projetofinal.model.Usuario;
import br.com.monthalcantara.projetofinal.exception.SenhaInvalidaException;
import br.com.monthalcantara.projetofinal.security.jwt.JwtService;
import br.com.monthalcantara.projetofinal.service.interfaces.UsuarioService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService userService;
    @Autowired
    private JwtService jwtService;


    @GetMapping("/protected")
    @ApiOperation("Busca todos os Usuários")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Usuários não localizados"),
            @ApiResponse(code = 403, message = "Você não possui permissão para visualizar este recurso"),
            @ApiResponse(code = 401, message = "Você não possui credenciais de autenticação válidas"),
            @ApiResponse(code = 200, message = "Usuários localizados")})
    public ResponseEntity findAll(Pageable pageable) {
        log.info("Buscando todos os usuarios cadastrados");
        return new ResponseEntity(this.userService.findAll(pageable), HttpStatus.OK);
    }


    @GetMapping("/protected/{id}")
    @ApiOperation("Busca um usuário pelo Id")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Usuário não localizado"),
            @ApiResponse(code = 403, message = "Você não possui permissão para visualizar este recurso"),
            @ApiResponse(code = 401, message = "Você não possui credenciais de autenticação válidas"),
            @ApiResponse(code = 200, message = "Usuário localizado")})
    public ResponseEntity findById(@PathVariable("id") Long id) {
        log.info("Buscando usuario cadastrados pelo id: {} ", id);
        return new ResponseEntity(this.userService.findById(id), HttpStatus.OK);
    }

    @GetMapping("protected/login/{login}")
    @ApiOperation("Busca um usuário pelo Login")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Usuário não localizado"),
            @ApiResponse(code = 403, message = "Você não possui permissão para visualizar este recurso"),
            @ApiResponse(code = 401, message = "Você não possui credenciais de autenticação válidas"),
            @ApiResponse(code = 200, message = "Usuário localizado")})
    public ResponseEntity byLogin(@PathVariable("login") String login) {
        log.info("Buscando usuario cadastrados pelo login: {} ", login);
        return new ResponseEntity(this.userService.findByLogin(login), HttpStatus.OK);
    }


    @PostMapping
    @ApiOperation("Cria um novo Usuário")
    @Cacheable("usuarios")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Não foi possível criar o Usuário"),
            @ApiResponse(code = 403, message = "Você não possui permissão para visualizar este recurso"),
            @ApiResponse(code = 401, message = "Você não possui credenciais de autenticação válidas"),
            @ApiResponse(code = 200, message = "Usuário criado"),
            @ApiResponse(code = 201, message = "Usuário criado com sucesso")})
    public UsuarioDTO save(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        log.info("Cadastrando um novo usuario");
        return new UsuarioDTO(this.userService.save(usuarioDTO.build()));
    }

    @PostMapping("/auth")
    @ApiOperation("Gera um Token de Acesso")
    @Cacheable("usuarios")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Usuário não localizado"),
            @ApiResponse(code = 200, message = "Token gerado"),
            @ApiResponse(code = 201, message = "Token gerado com sucesso")})
    public TokenDTO authenticate(@RequestBody CredenciaisDTO userLogin) {
        log.info("Autenticando um usuario");
        try {
            Usuario user = Usuario.builder()
                    .login(userLogin.getLogin())
                    .password(userLogin.getPassword())
                    .build();
            UserDetails authenticate = userService.autenticar(user);
            String token = jwtService.createToken(user);
            return new TokenDTO(userLogin.getLogin(), token);

        } catch (UsernameNotFoundException | SenhaInvalidaException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @DeleteMapping("/admin/{id}")
    @ApiOperation("Exclui um Usuário")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Usuário não localizado"),
            @ApiResponse(code = 403, message = "Você não possui permissão para visualizar este recurso"),
            @ApiResponse(code = 401, message = "Você não possui credenciais de autenticação válidas"),
            @ApiResponse(code = 200, message = "Usuário localizado"),
            @ApiResponse(code = 201, message = "Usuário excluído com sucesso")})
    public void deleteById(@PathVariable("id") Long id) {
        log.info("Excluindo um usuario cadastrado pelo id: {} ", id);
        this.userService.deleteById(id);
    }


    @PutMapping("/admin/{id}")
    @ApiOperation("Atualiza um Usuário")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Usuário não localizado"),
            @ApiResponse(code = 403, message = "Você não possui permissão para visualizar este recurso"),
            @ApiResponse(code = 401, message = "Você não possui credenciais de autenticação válidas"),
            @ApiResponse(code = 200, message = "Usuário localizado"),
            @ApiResponse(code = 201, message = "Usuário atualizado com sucesso")})
    public ResponseEntity update(@PathVariable(value = "id") Long id, @Valid @RequestBody Usuario user) {
        log.info("Atualizando um usuario cadastrado pelo id: {} ", id);
        Usuario usuario = this.userService.updateUsuario(id, user);
        return new ResponseEntity(new UsuarioDTO(usuario), HttpStatus.NO_CONTENT);

    }


}
