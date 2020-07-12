package br.com.monthalcantara.projetofinal.repository;

import br.com.monthalcantara.projetofinal.model.Evento;
import br.com.monthalcantara.projetofinal.enums.Level;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface EventoRepository extends PagingAndSortingRepository<Evento, Long> {
    Optional<List<Evento>> findByLevel(Level level);

    Optional<List<Evento>> findByOrigem(String origem);

    Optional<List<Evento>> findByDescricao(String descricao);

    Optional<Evento> findById(Long id);

}
