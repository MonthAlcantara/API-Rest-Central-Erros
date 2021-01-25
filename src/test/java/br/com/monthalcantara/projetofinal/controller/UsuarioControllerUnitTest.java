package br.com.monthalcantara.projetofinal.controller;

import br.com.monthalcantara.projetofinal.dto.UsuarioDTO;
import br.com.monthalcantara.projetofinal.model.Usuario;
import br.com.monthalcantara.projetofinal.service.interfaces.UsuarioService;
import br.com.monthalcantara.projetofinal.util.UsuarioFactory;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;


class UsuarioControllerUnitTest {


    private UsuarioService usuarioService;
    private UsuarioController controller;
    private Pageable pageable;
    private UriComponentsBuilder builder;
    private UsuarioDTO usuarioTesteDto;
    private Usuario usuario, usuarioTeste;


    @BeforeEach
    void init() {
        builder = UriComponentsBuilder.newInstance();
        pageable = PageRequest.of(1, 1);
        usuarioService = Mockito.mock(UsuarioService.class);
        controller = new UsuarioController(usuarioService);
        usuario = UsuarioFactory.geraAdminSalvo();
        usuarioTeste = geraNovoUsuario("Teste");
        usuarioTesteDto = geraNovoUsuarioDto("Teste");
    }

    @Test
    @DisplayName("Deveria retornar uma página de Usuarios DTO")
    void findAll() {
        Mockito.when(usuarioService.findAll(pageable)).thenReturn(geraPageUsuarios());

        ResponseEntity responseEntity = controller.findAll(pageable);

        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(responseEntity.getBody(), geraPageUsuariosDto());
    }

    @Test
    @DisplayName("Deveria retornar um Usuario DTO pelo id")
    void findById() {
        Mockito.when(usuarioService.findById(1L)).thenReturn(usuarioTeste);

        ResponseEntity responseEntity = controller.findById(1L);

        Assert.assertEquals(responseEntity.getBody(), usuarioTeste);
        Assert.assertNotNull(responseEntity.getBody());
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    @DisplayName("Deveria retornar um Usuario DTO pelo login")
    void byLogin() {

        Mockito.when(usuarioService.findByLogin("Teste")).thenReturn(usuarioTeste);

        ResponseEntity responseEntity = controller.byLogin(usuarioTeste.getLogin());

        Assert.assertNotNull(responseEntity.getBody());
        Assert.assertEquals(responseEntity.getBody(), usuarioTeste);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    @DisplayName("Deveria salvar um Usuario")
    void save() {
        Mockito.when(usuarioService.save(Mockito.any(Usuario.class))).thenReturn(usuario);
        ResponseEntity responseEntity = controller.save(usuarioTesteDto, builder);
        Assert.assertNotNull(responseEntity.getBody());
        Assert.assertEquals(responseEntity.getBody().getClass(), UsuarioDTO.class);
        Assert.assertEquals(responseEntity.getHeaders().getLocation().toString(), String.format("/v1/usuarios/%s", usuario.getId()));

    }


    public Page<Usuario> geraPageUsuarios() {
        List<Usuario> usuarioDTO = Arrays.asList(geraNovoUsuario("Teste1"),
                geraNovoUsuario("Teste2"),
                geraNovoUsuario("Teste3"));

        return new PageImpl<>(usuarioDTO);
    }

    public Page<UsuarioDTO> geraPageUsuariosDto() {
        List<UsuarioDTO> usuarioDTO = Arrays.asList(geraNovoUsuarioDto("Teste1"),
                geraNovoUsuarioDto("Teste2"),
                geraNovoUsuarioDto("Teste3"));

        return new PageImpl<>(usuarioDTO);
    }

    public Usuario geraNovoUsuario(String login) {
        return Usuario
                .builder()
                .admin(true)
                .login(login)
                .password("senha")
                .build();
    }

    public UsuarioDTO geraNovoUsuarioDto(String login) {
        return UsuarioDTO
                .builder()
                .admin(true)
                .login(login)
                .password("senha")
                .build();
    }
}
