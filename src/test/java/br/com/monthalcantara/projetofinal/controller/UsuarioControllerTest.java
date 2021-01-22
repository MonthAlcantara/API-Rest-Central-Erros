package br.com.monthalcantara.projetofinal.controller;

import br.com.monthalcantara.projetofinal.dto.UsuarioDTO;
import br.com.monthalcantara.projetofinal.model.Usuario;
import br.com.monthalcantara.projetofinal.service.implementacoes.UsuarioServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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

    UsuarioServiceImpl usuarioService = Mockito.mock(UsuarioServiceImpl.class);

    @Test
    @DisplayName("Deve criar um novo usuario")
    public void deveCriarUsuario() throws Exception {
        UsuarioDTO usuario = geradorDeUsuario();
        Usuario toModel = usuario.toModel();
        BDDMockito.given(usuarioService.save(any(Usuario.class))).willReturn(toModel);

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
                .andExpect(jsonPath("admin").value(true));

    }

    @Test
    @DisplayName("Não deve criar usuario sem login")
    public void naoDeveCriarUsarioSemLogin() throws Exception {
        UsuarioDTO usuario = geradorDeUsuario();


        BDDMockito.given(usuarioService.save(Mockito.any(Usuario.class))).willReturn(usuario.toModel());
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
        BDDMockito.given(usuarioService.save(Mockito.any(Usuario.class))).willReturn(usuario.toModel());
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
    public void deveAtualizarUsuario() throws Exception {

        Usuario novoUsuario = geradorDeUsuario().toModel();

        String json = geradorDeJson(novoUsuario);

        Mockito.when(usuarioService.updateUsuario(anyLong(), any(Usuario.class))).thenReturn(novoUsuario);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(USUARIO_API.concat("/admin/1"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(request)
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("login").value("Teste 01"))
                .andExpect(jsonPath("password").value(novoUsuario.getPassword()))
                .andExpect(jsonPath("admin").value(true));
    }

    @Test
    @DisplayName("Deve deletar um usuario")
    public void deveDeletarUsuaio() throws Exception {

        Mockito.doNothing().when(usuarioService).deleteById(anyLong());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(USUARIO_API.concat("/admin/1"));

        mvc
                .perform(request)
                .andExpect(status().isNoContent());

    }

    private UsuarioDTO geradorDeUsuario() {
        return UsuarioDTO.builder()
                .login("Teste 01")
                .password(new BCryptPasswordEncoder().encode("123"))
                .admin(true)
                .build();
    }

    private String geradorDeJson(Object o) throws Exception {
        return new ObjectMapper().writeValueAsString(o);
    }
}
