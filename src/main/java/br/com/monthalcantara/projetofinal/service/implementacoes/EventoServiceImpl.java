package br.com.monthalcantara.projetofinal.service.implementacoes;

import br.com.monthalcantara.projetofinal.dto.EventoDTO;
import br.com.monthalcantara.projetofinal.model.Evento;
import br.com.monthalcantara.projetofinal.enums.Level;
import br.com.monthalcantara.projetofinal.exception.RegraNegocioException;
import br.com.monthalcantara.projetofinal.repository.EventoRepository;
import br.com.monthalcantara.projetofinal.service.interfaces.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventoServiceImpl implements EventoService {
    @Autowired
    EventoRepository eventoRepository;

    @Override
    public Page<EventoDTO> findAll(Pageable pageable) {
        Page<Evento> listaEvento = this.eventoRepository.findAll(pageable);
        List<EventoDTO> listaEventoDTO = new ArrayList<>();

        for (Evento evento : listaEvento) {
            listaEventoDTO.add(new EventoDTO(evento));
        }
        return new PageImpl<>(listaEventoDTO);
    }

    @Override
    public Page<EventoDTO> findByDescricao(String descricao, Pageable pageable) {
        List<EventoDTO> listaEventoDTO = new ArrayList<>();
        List<Evento> listaEvento = this.eventoRepository
                .findByDescricao(descricao, pageable)
                .orElseThrow(() -> new RegraNegocioException("Não encontrado eventos com esta descrição: " + descricao));

        for (Evento evento : listaEvento) {
            listaEventoDTO.add(new EventoDTO(evento));
        }
        return new PageImpl<>(listaEventoDTO);

    }

    @Override
    public EventoDTO findById(Long id) {
        return this.eventoRepository
                .findById(id)
                .map(evento -> new EventoDTO(evento))
                .orElseThrow(() -> new RegraNegocioException("Não encontrado evento com este Id"));
    }

    @Override
    public Evento save(Evento evento) {
        return this.eventoRepository.save(evento);
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        this.eventoRepository.deleteById(id);
    }

    @Override
    public Page<EventoDTO> findByOrigem(String origem, Pageable pageable) {
        List<EventoDTO> listaEventoDTO = new ArrayList<>();
        return this.eventoRepository.findByOrigem(origem, pageable).map(evento -> {
            for (Evento event : evento) {
                listaEventoDTO.add(new EventoDTO(event));
            }
            return new PageImpl(listaEventoDTO);
        }).orElseThrow(() -> new RegraNegocioException("Não encontrado eventos com esta origem: " + origem));

    }

    @Override
    public Page<EventoDTO> findByLevel(Level level, Pageable pageable) {
        List<EventoDTO> listaEventoDTO = new ArrayList<>();

        return this.eventoRepository.findByLevel(level, pageable).map(eventos -> {
            for (Evento e : eventos) {
                listaEventoDTO.add(new EventoDTO(e));
            }
            return new PageImpl<>(listaEventoDTO);
        }).orElseThrow(() -> new RegraNegocioException("Não encontrado eventos com este level: " + level));

    }
}
