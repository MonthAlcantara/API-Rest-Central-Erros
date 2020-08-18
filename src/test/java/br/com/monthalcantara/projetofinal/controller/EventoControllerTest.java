package br.com.monthalcantara.projetofinal.controller;

import br.com.monthalcantara.projetofinal.enums.Level;
import br.com.monthalcantara.projetofinal.model.Evento;
import br.com.monthalcantara.projetofinal.service.interfaces.EventoService;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class EventoControllerTest {
    static String EVENTO_API = "/v1/eventos";

    @MockBean
    EventoService eventoService;

    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("Deve criar um novo evento")
    public void deveCriarEvento() throws Exception {
        Evento evento = geradorDeEventos();

        String json = new ObjectMapper().writeValueAsString(evento);
        BDDMockito.given(eventoService.save(Mockito.any(Evento.class))).willReturn(evento);

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


}
