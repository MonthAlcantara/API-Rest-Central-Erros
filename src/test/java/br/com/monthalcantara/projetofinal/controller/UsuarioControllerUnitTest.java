package br.com.monthalcantara.projetofinal.controller;

import br.com.monthalcantara.projetofinal.dto.UsuarioDTO;
import br.com.monthalcantara.projetofinal.service.interfaces.UsuarioService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

class UsuarioControllerUnitTest {


    private UsuarioService usuarioService = Mockito.mock(UsuarioService.class);
    private UsuarioController controller = new UsuarioController(usuarioService);
    private Pageable pageable = PageRequest.of(1, 1);
    private UsuarioDTO usuarioTeste;


    @BeforeEach
    void init() {
        usuarioTeste = geraNovoUsuarioDTO("Teste");
    }

    @Test
    @DisplayName("Deveria retornar uma página de Usuarios DTO")
    void findAll() {
        Mockito.when(usuarioService.findAll(pageable)).thenReturn(geraPageUsuariosDTO());

        ResponseEntity responseEntity = controller.findAll(pageable);

        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(responseEntity.getBody(), geraPageUsuariosDTO());
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
    @Disabled
    @DisplayName("Deveria salvar um Usuario")
    void save() {

    }


    public Page<UsuarioDTO> geraPageUsuariosDTO() {
        List<UsuarioDTO> usuarioDTOS = Arrays.asList(geraNovoUsuarioDTO("Teste1"),
                geraNovoUsuarioDTO("Teste2"),
                geraNovoUsuarioDTO("Teste3"));

        return new PageImpl<>(usuarioDTOS);
    }

    public UsuarioDTO geraNovoUsuarioDTO(String login) {
        return UsuarioDTO
                .builder()
                .admin(true)
                .login(login)
                .password("senha")
                .build();
    }
}
