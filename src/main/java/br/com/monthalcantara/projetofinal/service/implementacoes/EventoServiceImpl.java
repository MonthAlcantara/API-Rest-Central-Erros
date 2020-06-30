package br.com.monthalcantara.projetofinal.service.implementacoes;

import br.com.monthalcantara.projetofinal.dto.EventoDTO;
import br.com.monthalcantara.projetofinal.entity.Evento;
import br.com.monthalcantara.projetofinal.enums.Level;
import br.com.monthalcantara.projetofinal.repository.EventoRepository;
import br.com.monthalcantara.projetofinal.service.interfaces.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventoServiceImpl implements EventoService {
    @Autowired
    EventoRepository eventoRepository;

    @Override
    public List<EventoDTO> findAll(Pageable pageable) {
        List<Evento> listaEvento = this.eventoRepository.findAll();
        List<EventoDTO> listaEventoDTO = new ArrayList<>();

        for (Evento evento : listaEvento) {
            listaEventoDTO.add(new EventoDTO(evento));
        }
        return listaEventoDTO;
    }

    @Override
    public List<EventoDTO> findByDescricao(String descricao) {
        List<Evento> listaEvento = this.eventoRepository.findByDescricao(descricao);
        List<EventoDTO> listaEventoDTO = new ArrayList<>();

        for (Evento evento : listaEvento) {
            listaEventoDTO.add(new EventoDTO(evento));
        }
        return listaEventoDTO;

    }

    @Override
    public EventoDTO findById(Long id) {
        Optional<Evento> evento = this.eventoRepository.findById(id);
        if (evento.isPresent()) {
            return new EventoDTO(evento.get());
        }
        return null;

    }

    @Override
    public Evento save(Evento evento) {
        return this.eventoRepository.save(evento);
    }

    @Override
    public void deleteById(Long id) {
        this.eventoRepository.deleteById(id);
    }

    @Override
    public List<EventoDTO> findByOrigem(String origem, Pageable pageable) {
        List<Evento> listaEvento = this.eventoRepository.findByOrigem(origem);
        List<EventoDTO> listaEventoDTO = new ArrayList<>();

        for (Evento evento : listaEvento) {
            listaEventoDTO.add(new EventoDTO(evento));
        }
        return listaEventoDTO;
    }

    @Override
    public List<EventoDTO> findByLevel(Level level) {
        List<Evento> listaEvento = this.eventoRepository.findByLevel(level);
        List<EventoDTO> listaEventoDTO = new ArrayList<>();

        for (Evento evento : listaEvento) {
            listaEventoDTO.add(new EventoDTO(evento));
        }
        return listaEventoDTO;
    }
}
