package br.com.monthalcantara.projetofinal.service.interfaces;

import br.com.monthalcantara.projetofinal.dto.EventoDTO;
import br.com.monthalcantara.projetofinal.enums.Level;
import br.com.monthalcantara.projetofinal.model.Evento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventoService {

    Page<EventoDTO> findAll(Pageable pageable);

    Page<EventoDTO> findByDescricao(String descricao, Pageable pageable);

    EventoDTO findById(Long id);

    Evento save(Evento evento);

    void deleteById(Long id);

    Page<EventoDTO> findByLevel(Level level, Pageable pageable);

}
