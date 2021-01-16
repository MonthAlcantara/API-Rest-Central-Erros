package br.com.monthalcantara.projetofinal.controller;

import br.com.monthalcantara.projetofinal.dto.CredenciaisDTO;
import br.com.monthalcantara.projetofinal.dto.TokenDTO;
import br.com.monthalcantara.projetofinal.exception.SenhaInvalidaException;
import br.com.monthalcantara.projetofinal.model.Usuario;
import br.com.monthalcantara.projetofinal.security.jwt.JwtService;
import br.com.monthalcantara.projetofinal.service.implementacoes.UserDetailsServiceImpl;
import br.com.monthalcantara.projetofinal.service.interfaces.UsuarioService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;

@Profile({"prod","test"})
@RestController
@Slf4j
@RequestMapping("/v1/usuarios")
public class AutenticacaoController {



    private UserDetailsServiceImpl userService;
    private JwtService jwtService;

    public AutenticacaoController(UserDetailsServiceImpl userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/auth")
    @ApiOperation("Gera um Token de Acesso")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Usuário não localizado"),
            @ApiResponse(code = 200, message = "Token gerado"),
            @ApiResponse(code = 201, message = "Token gerado com sucesso")})
    public TokenDTO authenticate(@RequestBody CredenciaisDTO userLogin, HttpSession session) {
        try {
            Usuario user = Usuario.builder()
                    .login(userLogin.getLogin())
                    .password(userLogin.getPassword())
                    .build();
            UserDetails authenticate = userService.autenticar(user);
            String token = jwtService.createToken(user);
            log.info("Autenticando um usuario");
            return new TokenDTO(userLogin.getLogin(), token);

        } catch (UsernameNotFoundException | SenhaInvalidaException e) {
            log.error("Erro ao autenticar usuario {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

}
