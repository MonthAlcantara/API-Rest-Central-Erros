package br.com.monthalcantara.projetofinal.controller;

import br.com.monthalcantara.projetofinal.controller.swagger.UsuarioControllerSwagger;
import br.com.monthalcantara.projetofinal.dto.UsuarioDTO;
import br.com.monthalcantara.projetofinal.model.Usuario;
import br.com.monthalcantara.projetofinal.service.interfaces.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/v1/usuarios")
public class UsuarioController implements UsuarioControllerSwagger {

    private UsuarioService userService;

    public UsuarioController(UsuarioService userService) {
        this.userService = userService;
    }

    public ResponseEntity findAll(Pageable pageable) {

        log.info("Recebida requisição de busca por todos os susuarios de forma paginada ");

        Page<Usuario> usuariosPage = this.userService.findAll(pageable);

        log.info("Realizada busca de todos os usuarios cadastrados");

        List<UsuarioDTO> usuario = usuariosPage.getContent().stream().map(UsuarioDTO::new).collect(Collectors.toList());

        log.info("Convertidos usuarios encontrados em usuariosDTO");

        return ResponseEntity
                .ok(new PageImpl<>(usuario));
    }

    public ResponseEntity findById(@PathVariable("id") Long id) {
        log.info("Buscando usuario cadastrados pelo id: {} ", id);
        return ResponseEntity
                .ok(this.userService.findById(id));
    }

    public ResponseEntity byLogin(@PathVariable("login") String login) {
        log.info("Buscando usuario cadastrados pelo login: {} ", login);
        return ResponseEntity
                .ok(this.userService.findByLogin(login));
    }

    public ResponseEntity save(@Valid @RequestBody UsuarioDTO usuarioDTO, UriComponentsBuilder builder) {

        log.info("Recebida solicitação para salvar o usuario: {}", usuarioDTO);

        Usuario usuario = this.userService.save(usuarioDTO.toModel());

        log.info("Novo usuario Cadastrado com id: {} ", usuario.getId());

        URI uri = builder.path("/v1/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();

        log.info("Criada Location do usuario {} ", uri.toString());

        return ResponseEntity
                .created(uri)
                .body(new UsuarioDTO(usuario));
    }

    public ResponseEntity deleteById(@PathVariable("id") Long id) {

        log.info("Recebida solicitação de exclusão para o usuario de id: {}", id);

        this.userService.deleteById(id);

        log.info("Excluído usuario cadastrado pelo id: {} ", id);

        return ResponseEntity
                .noContent()
                .build();
    }

    public ResponseEntity update(@PathVariable(value = "id") Long id, @Valid @RequestBody Usuario user) {

        log.info("Recebida solicitação de atualização para o usuario de id: {}", id);

        Usuario usuario = this.userService.updateUsuario(id, user);

        log.info("Atualizando um usuario cadastrado pelo id: {} ", id);

        return ResponseEntity
                .ok(new UsuarioDTO(usuario));

    }
}
