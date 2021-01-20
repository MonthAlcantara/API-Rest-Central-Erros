package br.com.monthalcantara.projetofinal.controller;

import br.com.monthalcantara.projetofinal.dto.UsuarioDTO;
import br.com.monthalcantara.projetofinal.enums.Level;
import br.com.monthalcantara.projetofinal.model.Evento;
import br.com.monthalcantara.projetofinal.repository.EventoRepository;
import br.com.monthalcantara.projetofinal.service.implementacoes.EventoServiceImpl;
import br.com.monthalcantara.projetofinal.service.interfaces.EventoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureWebMvc
@WebMvcTest
public class EventoControllerTest {
    static String EVENTO_API = "/v1/eventos";

    @Autowired
    MockMvc mvc;
    @Mock
    private EventoService eventoService;
    @Mock
    private EventoRepository repository;
    private EventoController eventoController;
    private Evento evento;
    private UsuarioDTO usuario;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        eventoController = new EventoController(eventoService);
        evento = geradorDeEventos();
        usuario = geradorDeUsuario();
    }

    @Test
    @DisplayName("Deve criar um novo evento")
    public void deveCriarEvento() throws Exception {

        Mockito.when(eventoService.save(Mockito.any(Evento.class))).thenReturn(evento);
        String json = geradorDeJson(evento);

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

    private String geradorDeJson(Object o) throws Exception {
        return new ObjectMapper().writeValueAsString(o);
    }

}
