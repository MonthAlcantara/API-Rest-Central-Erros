package br.com.monthalcantara.projetofinal.controller;

import br.com.monthalcantara.projetofinal.controller.swagger.UsuarioControllerSwagger;
import br.com.monthalcantara.projetofinal.dto.UsuarioDTO;
import br.com.monthalcantara.projetofinal.model.Usuario;
import br.com.monthalcantara.projetofinal.service.interfaces.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@Slf4j
@RequestMapping("/v1/usuarios")
public class UsuarioController implements UsuarioControllerSwagger {

    private UsuarioService userService;

    public UsuarioController(UsuarioService userService) {
        this.userService = userService;
    }

    public ResponseEntity findAll(Pageable pageable) {
        log.info("Buscando todos os usuarios cadastrados");
        return new ResponseEntity(this.userService.findAll(pageable), HttpStatus.OK);
    }

    public ResponseEntity findById(@PathVariable("id") Long id) {
        log.info("Buscando usuario cadastrados pelo id: {} ", id);
        return new ResponseEntity(this.userService.findById(id), HttpStatus.OK);
    }

    public ResponseEntity byLogin(@PathVariable("login") String login) {
        log.info("Buscando usuario cadastrados pelo login: {} ", login);
        return new ResponseEntity(this.userService.findByLogin(login), HttpStatus.OK);
    }

    public ResponseEntity save(@Valid @RequestBody UsuarioDTO usuarioDTO, UriComponentsBuilder builder) {
        Usuario usuario = this.userService.save(usuarioDTO.toModel());

        URI uri = builder.path("/v1/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        log.info("Cadastrando um novo usuario");

        return ResponseEntity
                .created(uri)
                .body(new UsuarioDTO(usuario));
    }

    public void deleteById(@PathVariable("id") Long id) {
        log.info("Excluindo um usuario cadastrado pelo id: {} ", id);
        this.userService.deleteById(id);
    }

    public ResponseEntity update(@PathVariable(value = "id") Long id, @Valid @RequestBody Usuario user) {
        log.info("Atualizando um usuario cadastrado pelo id: {} ", id);
        Usuario usuario = this.userService.updateUsuario(id, user);
        return new ResponseEntity(new UsuarioDTO(usuario), HttpStatus.NO_CONTENT);

    }
}
