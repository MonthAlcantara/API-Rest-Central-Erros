package br.com.monthalcantara.projetofinal.service.interfaces;

import br.com.monthalcantara.projetofinal.dto.EventoDTO;
import br.com.monthalcantara.projetofinal.model.Evento;
import br.com.monthalcantara.projetofinal.enums.Level;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EventoService {

    List<EventoDTO> findAll(Pageable pageable);

    List<EventoDTO> findByDescricao(String descricao, Pageable pageable);

    EventoDTO findById(Long id);

    Evento save(Evento evento);

    void deleteById(Long id);

    List<EventoDTO> findByOrigem(String origem, Pageable pageable);

    List<EventoDTO> findByLevel(Level level, Pageable pageable);

}
