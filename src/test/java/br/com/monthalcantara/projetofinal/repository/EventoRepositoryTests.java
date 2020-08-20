package br.com.monthalcantara.projetofinal.repository;

import br.com.monthalcantara.projetofinal.enums.Level;
import br.com.monthalcantara.projetofinal.model.Evento;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;


@SpringBootTest
public class EventoRepositoryTests {
    Evento evento, eventoSalvo;

    @Autowired
    EventoRepository eventoRepository;


    @Test
    @DisplayName("Deve salvar um novo evento no banco de dados")
    void deveCriarEvento() {
        evento = geradorDeEvento();
        eventoSalvo = eventoRepository.save(evento);

        Assertions.assertThat(eventoSalvo).isNotNull();
    }

    @Test
    @DisplayName("Deve buscar um evento salvo pelo Id")
    void deveBuscarPeloId() {
        evento = geradorDeEvento();
        eventoSalvo = eventoRepository.save(evento);
        Assertions.assertThat(eventoRepository.findById(eventoSalvo.getId())).isPresent();
    }

    @Test
    @DisplayName("Não deve buscar evento inexistente")
    void naoDeveBuscarEventoInexistente() {
        Assertions.assertThat(eventoRepository.findById(1L)).isNotPresent();
    }

    @Test
    @DisplayName("Deve deletar um evento")
    void deveDeletarUmEvento() {
        evento = geradorDeEvento();
        eventoRepository.save(evento);
        eventoRepository.deleteById(1l);
        Assertions.assertThat(eventoRepository.findById(1L)).isNotPresent();
    }

    @Test
    @DisplayName("Deve atualizar um evento")
    void deveAtualizarEvento() {
        evento = geradorDeEvento();
        eventoSalvo = eventoRepository.save(evento);
        eventoSalvo.setDescricao("Teste2");
        eventoSalvo.setLevel(Level.INFO);
        eventoSalvo.setLog("Teste2");
        eventoSalvo = eventoRepository.save(eventoSalvo);
        Assertions.assertThat(eventoSalvo.getDescricao()).isNotEqualTo(evento.getDescricao());
        Assertions.assertThat(eventoSalvo.getId()).isEqualTo(evento.getId());
        Assertions.assertThat(eventoSalvo.getLevel()).isNotEqualTo(evento.getLevel());
        Assertions.assertThat(eventoSalvo.getLog()).isNotEqualTo(evento.getLog());

    }

    private Evento geradorDeEvento() {
        return Evento.builder().descricao("Teste")
                .level(Level.ERROR)
                .data(LocalDateTime.parse("2020-08-02T11:11:35"))
                .origem("Teste")
                .quantidade(1)
                .id(1L)
                .log("Teste").build();
    }

}
