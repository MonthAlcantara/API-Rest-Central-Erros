package br.com.monthalcantara.projetofinal.repository;

import br.com.monthalcantara.projetofinal.model.Evento;
import br.com.monthalcantara.projetofinal.enums.Level;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventoRepository extends PagingAndSortingRepository<Evento, Long> {
    Optional<List<Evento>> findByLevel(Level level, Pageable pageable);

    Optional<Page<Evento>> findByOrigem(String origem, Pageable pageable);

    Optional<List<Evento>> findByDescricao(String descricao, Pageable pageable);

    Optional<Evento> findById(Long id);

    Optional<Evento> findByData(LocalDateTime data);
}
