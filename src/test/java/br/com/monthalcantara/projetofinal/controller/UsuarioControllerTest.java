package br.com.monthalcantara.projetofinal.controller;

import br.com.monthalcantara.projetofinal.dto.UsuarioDTO;
import br.com.monthalcantara.projetofinal.model.Usuario;
import br.com.monthalcantara.projetofinal.repository.UsuarioRepository;
import br.com.monthalcantara.projetofinal.security.jwt.JwtService;
import br.com.monthalcantara.projetofinal.service.implementacoes.UsuarioServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {

    static String USUARIO_API = "/v1/usuarios";


    @Autowired
    MockMvc mvc;

    @MockBean
    Pageable pageable;

    @MockBean
    JwtService jwtService;

    @MockBean
    UsuarioRepository usuarioRepository;

    @MockBean
    UsuarioServiceImpl usuarioService;

    @Test
    @DisplayName("Deve criar um novo usuario")
    public void deveCriarUsuario() throws Exception {
        UsuarioDTO usuario = geradorDeUsuario();
        BDDMockito.given(usuarioService.save(any(Usuario.class))).willReturn(usuario.build());

        String json = geradorDeJson(usuario);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(USUARIO_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("login").value("Teste 01"))
                .andExpect(jsonPath("admin").value(true))
                .andExpect(jsonPath("password").value("123"));

    }

    @Test
    @DisplayName("Não deve criar usuario sem login")
    public void naoDeveCriarUsarioSemLogin() throws Exception {
        UsuarioDTO usuario = geradorDeUsuario();


        BDDMockito.given(usuarioService.save(Mockito.any(Usuario.class))).willReturn(usuario.build());
        usuario.setLogin(null);
        String json = geradorDeJson(usuario);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(USUARIO_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(request)
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("Não deve criar usuario sem senha")
    public void naoDeveCriarUsarioSemSenha() throws Exception {
        UsuarioDTO usuario = geradorDeUsuario();


        BDDMockito.given(usuarioService.save(Mockito.any(Usuario.class))).willReturn(usuario.build());
        usuario.setPassword(null);
        String json = geradorDeJson(usuario);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(USUARIO_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(request)
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("Deve atualizar um usuario")
    @Disabled
    public void deveAtualizarUsuario() throws Exception {

        Usuario novoUsuario = geradorDeUsuario().build();
        novoUsuario.setAdmin(false);
        String json = geradorDeJson(novoUsuario);

        BDDMockito.given(usuarioService.updateUsuario(anyLong(), any(Usuario.class))).willReturn(novoUsuario);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(USUARIO_API.concat("/admin/1"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("login").value("Teste 01"))
                .andExpect(jsonPath("password").value("123"))
                .andExpect(jsonPath("admin").value(false));
    }

    @Test
    @DisplayName("Deve deletar um usuario")
    @Disabled
    public void deveDeletarUsuaio() throws Exception {

        BDDMockito.given(usuarioRepository.findById(anyLong()))
                .willReturn(Optional.of(Usuario.builder().id(11L).build()));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(USUARIO_API.concat("/11"));

        mvc
                .perform(request)
                .andExpect(status().isNoContent());

    }

    private UsuarioDTO geradorDeUsuario() {
        return UsuarioDTO.builder()
                .login("Teste 01")
                .password("123")
                .admin(true)
                .build();
    }

    private String geradorDeJson(Object o) throws Exception {
        return new ObjectMapper().writeValueAsString(o);
    }


}
