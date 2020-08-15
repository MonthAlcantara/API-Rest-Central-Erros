package br.com.monthalcantara.projetofinal.controller;

import br.com.monthalcantara.projetofinal.dto.UsuarioDTO;
import br.com.monthalcantara.projetofinal.model.Usuario;
import br.com.monthalcantara.projetofinal.security.jwt.JwtService;
import br.com.monthalcantara.projetofinal.service.implementacoes.UsuarioServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    UsuarioServiceImpl usuarioService;

    @Test
    @DisplayName("Deve criar um novo usuario")
    public void deveCriarUsuario() throws Exception {
        UsuarioDTO usuario = geradorDeUsuario();
        BDDMockito.given(usuarioService.save(Mockito.any(Usuario.class))).willReturn(usuario.build());

        String json = new ObjectMapper().writeValueAsString(usuario);

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

    private UsuarioDTO geradorDeUsuario(){
        return UsuarioDTO.builder()
                .login("Teste 01")
                .password("123")
                .admin(true)
                .build();
    }

}
