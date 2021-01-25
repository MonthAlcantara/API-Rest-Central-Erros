package br.com.monthalcantara.projetofinal.controller;

import br.com.monthalcantara.projetofinal.enums.Level;
import br.com.monthalcantara.projetofinal.model.Evento;
import br.com.monthalcantara.projetofinal.service.interfaces.EventoService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;


public class EventoControllerTest {

    private EventoService eventoService;
    private EventoController eventoController;
    private Evento eventoSalvo, eventoNaoSalvo;

    @BeforeEach
    public void init() {
        eventoService = Mockito.mock(EventoService.class);
        eventoController = new EventoController(eventoService);
        eventoSalvo = geradorDeEvento();
        eventoNaoSalvo = geradorDeEventoNaoSalvo();
    }

    @Test
    @DisplayName("Deveria salvar novo evento")
    void test() {
        Mockito.when(eventoService.save(eventoNaoSalvo)).thenReturn(eventoSalvo);
        ResponseEntity responseEntity = eventoController.create(eventoNaoSalvo);

        Assert.assertNotNull(responseEntity.getBody());
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
    }

    private Evento geradorDeEvento() {
        return Evento.builder()
                .descricao("Teste")
                .level(Level.ERROR)
                .data(LocalDateTime.of(2020, 8, 02, 11, 11, 35))
                .origem("Teste")
                .quantidade(1)
                .id(1L)
                .log("Teste")
                .build();
    }

    private Evento geradorDeEventoNaoSalvo() {
        return Evento.builder()
                .descricao("Teste")
                .level(Level.ERROR)
                .data(LocalDateTime.of(2020, 8, 02, 11, 11, 35))
                .origem("Teste")
                .quantidade(1)
                .log("Teste")
                .build();
    }
}