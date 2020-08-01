package br.com.monthalcantara.projetofinal;


import br.com.monthalcantara.projetofinal.service.interfaces.EventoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class EventoControllerTest {

    static String EVENTO_API = "/v1/eventos";

    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("Deve criar um evento com sucesso")
    public void criaEvento() throws Exception {
        String json = new ObjectMapper().writeValueAsString(null);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(EVENTO_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("level").value(""))
                .andExpect(jsonPath("descricao").value(""))
                .andExpect(jsonPath("origem").value(""))
                .andExpect(jsonPath("data").value(""))
                .andExpect(jsonPath("quantidade").value(""));

    }
}
