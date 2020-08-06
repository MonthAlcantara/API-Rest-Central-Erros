package br.com.monthalcantara.projetofinal;

import br.com.monthalcantara.projetofinal.enums.Level;
import br.com.monthalcantara.projetofinal.model.Evento;
import br.com.monthalcantara.projetofinal.service.interfaces.EventoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class EventoControllerTest {
    static String EVENTO_API = "/v1/eventos";

    @Autowired
    EventoService eventoService;

    @Test
    @DisplayName("Deve criar um novo evento")
    @Disabled
    public void deveCriarEvento() {
        Evento evento = Evento.builder().descricao("Teste")
                .level(Level.ERROR)
                .data(LocalDateTime.parse("2020-08-02T11:11:35"))
                .origem("Teste")
                .quantidade(1)
                .id(1L)
                .log("teste").build();
        Evento save = eventoService.save(evento);
        save.setData(LocalDateTime.parse("2020-08-02T11:11:35"));
        Assertions.assertEquals(evento, save);
    }
}
