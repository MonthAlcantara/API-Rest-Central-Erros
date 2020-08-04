package br.com.monthalcantara.projetofinal;

import br.com.monthalcantara.projetofinal.enums.Level;
import br.com.monthalcantara.projetofinal.model.Evento;
import br.com.monthalcantara.projetofinal.service.interfaces.EventoService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class EventoRepositoryTests {

    Evento evento, eventoSalvo;

    @Autowired
    EventoService eventoService;


    @BeforeEach
    void carregarContexto() {
        evento = Evento.builder().descricao("Teste")
                .level(Level.ERROR)
                .data(LocalDateTime.parse("2020-08-02T11:11:35"))
                .origem("Teste")
                .quantidade(1)
                .id(1L)
                .log("teste").build();
        eventoSalvo = eventoService.save(evento);
    }

    @Test
    @DisplayName("Deve criar um novo evento")
    void deveCriarEvento() {

        Assertions.assertThat(eventoSalvo).isNotNull();
    }

    @Test
    @DisplayName("Deve buscar pelo Id")
    void deveBuscarPeloId(){
        Assertions.assertThat(eventoService.findById(1L)).isNotNull();
    }

    @Test
    @Disabled
    @DisplayName("Deve apagar um evento")
    void deveApagarEvento() {

        eventoService.deleteById(1L);
        Assertions.assertThat("").isNull();
    }
}
