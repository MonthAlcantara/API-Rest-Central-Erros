package br.com.monthalcantara.projetofinal.service;

import br.com.monthalcantara.projetofinal.dto.EventoDTO;
import br.com.monthalcantara.projetofinal.enums.Level;
import br.com.monthalcantara.projetofinal.exception.RecursoNotFound;
import br.com.monthalcantara.projetofinal.model.Evento;
import br.com.monthalcantara.projetofinal.repository.EventoRepository;
import br.com.monthalcantara.projetofinal.service.implementacoes.EventoServiceImpl;
import br.com.monthalcantara.projetofinal.service.interfaces.EventoService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
public class EventoServiceTest {
    Evento evento, eventoSalvo;

    @Autowired
    EventoService eventoService;
    @MockBean
    EventoRepository eventoRepository;


    @Test
    @DisplayName("Deve criar um novo evento")
    void deveCriarEvento() {
        evento = geradorDeEvento();

        BDDMockito.given(eventoRepository.save(Mockito.any(Evento.class))).willReturn(evento);

        eventoSalvo = eventoService.save(evento);

        Assertions.assertThat(eventoSalvo).isNotNull();
    }

    @Test
    @DisplayName("Deve buscar pelo Id")
    void deveBuscarPeloId() {
        evento = geradorDeEvento();
        BDDMockito.given(eventoRepository.findById(Mockito.anyLong())).willReturn(Optional.of(evento));

        EventoDTO eventoDTO = eventoService.findById(1L);

        Assertions.assertThat(eventoDTO).isNotNull();
    }

    @Test
    @DisplayName("Deve lançar erro ao buscar Id inexistente")
    void deveLancarErroBuscarIdInexistente() {

        BDDMockito.given(eventoRepository.findById(2L)).willReturn(Optional.empty());

        RuntimeException runtimeException = assertThrows(RecursoNotFound.class, () -> eventoService.findById(2L));

        assertTrue(runtimeException.getMessage().contains("Não encontrado evento com este Id"));
    }

    @Test
    @DisplayName("Deve deletar um evento")
    void deveDeletarUmEvento() {
        RuntimeException runtimeException = assertThrows(RecursoNotFound.class, () ->
                eventoService.deleteById(1L));

        assertTrue(runtimeException.getMessage().contains("Não encontrado evento com este Id"));
    }

    @Test
    @DisplayName("Deve lançar erro ao tentar excluir evento com Id inexistente")
    void DeveLancarErroExcluirIdInexistente() {
        BDDMockito.given(eventoRepository.findById(Mockito.anyLong())).willReturn(Optional.empty());

        RuntimeException runtimeException = assertThrows(RecursoNotFound.class, () ->
                eventoService.deleteById(1L));

        assertTrue(runtimeException.getMessage().contains("Não encontrado evento com este Id"));
    }

    @Test
    @DisplayName("Deve atualizar um evento")
    void deveAtualizarEvento() {
        evento = geradorDeEvento();

        BDDMockito.given(eventoRepository.save(Mockito.any(Evento.class))).willReturn(new Evento());

        Assertions.assertThat(eventoService.save(evento)).isNotNull();

    }

    private Evento geradorDeEvento() {
        return Evento.builder()
                .descricao("Teste")
                .level(Level.ERROR)
                .data(LocalDateTime.parse("2020-08-02T11:11:35"))
                .origem("Teste")
                .quantidade(1)
                .id(1L)
                .log("Teste")
                .build();
    }

}
