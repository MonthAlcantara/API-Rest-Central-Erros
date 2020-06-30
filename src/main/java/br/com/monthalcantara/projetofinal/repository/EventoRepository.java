package br.com.monthalcantara.projetofinal.repository;

import br.com.monthalcantara.projetofinal.entity.Evento;
import br.com.monthalcantara.projetofinal.enums.Level;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByLevel(Level level);

    List<Evento> findByOrigem(String origem);

    List<Evento> findByDescricao(String descricao);


}
