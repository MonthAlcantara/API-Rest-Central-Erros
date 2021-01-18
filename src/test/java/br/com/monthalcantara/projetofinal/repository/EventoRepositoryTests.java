package br.com.monthalcantara.projetofinal.repository;

import br.com.monthalcantara.projetofinal.enums.Level;
import br.com.monthalcantara.projetofinal.model.Evento;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;


@DataJpaTest
public class EventoRepositoryTests {
    private Evento evento, eventoSalvo;

    @Autowired
    private EventoRepository eventoRepository;

    @BeforeEach
    public void init(){
        evento = geradorDeEvento();
        eventoSalvo = eventoRepository.save(evento);
    }

    @Test
    @DisplayName("Deve salvar um novo evento no banco de dados")
    void deveCriarEvento() {

        Assertions.assertThat(eventoSalvo).isNotNull();
    }

    @Test
    @DisplayName("Deve buscar um evento salvo pelo Id")
    void deveBuscarPeloId() {
        Assertions.assertThat(eventoRepository.findById(eventoSalvo.getId())).isPresent();
    }

    @Test
    @DisplayName("Não deve buscar evento inexistente")
    void naoDeveBuscarEventoInexistente() {
        Assertions.assertThat(eventoRepository.findById(50L)).isNotPresent();
    }

    @Test
    @DisplayName("Deve deletar um evento")
    void deveDeletarUmEvento() {
        eventoRepository.deleteById(eventoSalvo.getId());
        Assertions.assertThat(eventoRepository.findById(eventoSalvo.getId())).isNotPresent();
    }

    @Test
    @DisplayName("Deve atualizar um evento")
    void deveAtualizarEvento() {

        eventoSalvo.setDescricao("Teste2");
        eventoSalvo.setLevel(Level.INFO);
        eventoSalvo.setLog("Teste2");

        eventoSalvo = eventoRepository.save(eventoSalvo);

        Assertions.assertThat(eventoSalvo.getDescricao()).isNotEqualTo(evento.getDescricao());
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
