package br.com.monthalcantara.projetofinal;

import br.com.monthalcantara.projetofinal.dto.EventoDTO;
import br.com.monthalcantara.projetofinal.enums.Level;
import br.com.monthalcantara.projetofinal.exception.RegraNegocioException;
import br.com.monthalcantara.projetofinal.model.Evento;
import br.com.monthalcantara.projetofinal.service.interfaces.EventoService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
                .log("Teste").build();
        eventoSalvo = eventoService.save(evento);
    }

    @Test
    @DisplayName("Deve criar um novo evento")
    void deveCriarEvento() {

        Assertions.assertThat(eventoSalvo).isNotNull();
    }

    @Test
    @DisplayName("Deve buscar pelo Id")
    void deveBuscarPeloId() {
        eventoSalvo = eventoService.save(evento);
        EventoDTO eventoDTO = eventoService.findById(eventoSalvo.getId());
        Assertions.assertThat(eventoDTO).isNotNull();
    }

    @Test
    @DisplayName("Deve lançar erro ao buscar Id inexistente")
    void deveLancarErroBuscarIdInexistente() {
        RuntimeException runtimeException = assertThrows(RegraNegocioException.class, () -> eventoService.findById(2L));
        assertTrue(runtimeException.getMessage().contains("Não encontrado evento com este Id"));
    }

    @Test
    @DisplayName("Deve deletar um evento")
    void deveDeletarUmEvento() {
        eventoService.deleteById(1l);
        RuntimeException runtimeException = assertThrows(RegraNegocioException.class, () ->
                eventoService.deleteById(1L));
        assertTrue(runtimeException.getMessage().contains("Não encontrado evento com este Id"));
    }

    @Test
    @DisplayName("Deve lançar erro ao tentar excluir evento com Id inexistente")
    void DeveLancarErroExcluirIdInexistente() {
        RuntimeException runtimeException = assertThrows(RegraNegocioException.class, () ->
                eventoService.deleteById(2L));
        assertTrue(runtimeException.getMessage().contains("Não encontrado evento com este Id"));
    }

    @Test
    @DisplayName("Deve atualizar um evento")
    void deveAtualizarEvento() {
        eventoSalvo.setDescricao("Teste2");
        eventoSalvo.setId(2L);
        eventoSalvo.setLevel(Level.INFO);
        eventoSalvo.setLog("Teste2");
        eventoSalvo = eventoService.save(eventoSalvo);
        Assertions.assertThat(eventoSalvo.getDescricao()).isNotEqualTo(evento.getDescricao());
        Assertions.assertThat(eventoSalvo.getId()).isNotEqualTo(evento.getId());
        Assertions.assertThat(eventoSalvo.getLevel()).isNotEqualTo(evento.getLevel());
        Assertions.assertThat(eventoSalvo.getLog()).isNotEqualTo(evento.getLog());

    }

}
