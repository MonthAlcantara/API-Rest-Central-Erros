package br.com.monthalcantara.projetofinal.controller;

import br.com.monthalcantara.projetofinal.dto.TokenDTO;
import br.com.monthalcantara.projetofinal.dto.UsuarioDTO;
import br.com.monthalcantara.projetofinal.enums.Level;
import br.com.monthalcantara.projetofinal.model.Evento;
import br.com.monthalcantara.projetofinal.model.Usuario;
import br.com.monthalcantara.projetofinal.security.jwt.JwtService;
import br.com.monthalcantara.projetofinal.service.implementacoes.EventoServiceImpl;
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
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class EventoControllerTest {
    static String EVENTO_API = "/v1/eventos";

    @MockBean
    EventoServiceImpl eventoService;

    @MockBean
    UserDetails authenticate;

    @MockBean
    TokenDTO dto;

    @MockBean
    private UsuarioServiceImpl userService;
    @MockBean
    private JwtService jwtService;

    @MockBean
    UserDetails userDetails;

    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("Deve criar um novo evento")
    public void deveCriarEvento() throws Exception {
        Evento evento = geradorDeEventos();
        UsuarioDTO usuario = geradorDeUsuario();
        BDDMockito.given(userService.autenticar(Mockito.any(Usuario.class))).willReturn(userDetails);

        BDDMockito.given(eventoService.save(Mockito.any(Evento.class))).willReturn(evento);
        String json = new ObjectMapper().writeValueAsString(evento);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(EVENTO_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(request)
                .andExpect(status().isCreated());

    }

    private Evento geradorDeEventos() {
        return Evento.builder()
                .descricao("Teste")
                .level(Level.ERROR)
                .data(LocalDateTime.parse("2020-08-02T11:11:35"))
                .origem("Teste")
                .quantidade(1)
                .id(1L)
                .log("Teste").build();
    }

    private UsuarioDTO geradorDeUsuario() {
        return UsuarioDTO.builder()
                .login("Teste 01")
                .password("123")
                .admin(true)
                .build();
    }

}
