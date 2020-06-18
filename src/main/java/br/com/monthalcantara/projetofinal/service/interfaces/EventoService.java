package br.com.monthalcantara.projetofinal.service.interfaces;

import br.com.monthalcantara.projetofinal.entity.Evento;
import br.com.monthalcantara.projetofinal.enums.Level;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface EventoService {

    List<Evento> findAll(Pageable pageable);

    List<Evento> findByDescricao(String descricao);

    Optional<Evento> findById(Long id);

    Evento save(Evento evento);

    void deleteById(Long id);

    List<Evento> findByOrigem(String origem, Pageable pageable);

    List<Evento> findByLevel(Level level);

}
