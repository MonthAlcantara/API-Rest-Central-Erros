package br.com.monthalcantara.projetofinal.service.implementacoes;

import br.com.monthalcantara.projetofinal.entity.Evento;
import br.com.monthalcantara.projetofinal.enums.Level;
import br.com.monthalcantara.projetofinal.repositories.EventoRepository;
import br.com.monthalcantara.projetofinal.service.interfaces.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventoServiceImpl implements EventoService {
    @Autowired
    EventoRepository eventoRepository;

    @Override
    public List<Evento> findAll(Pageable pageable) {
        return this.eventoRepository.findAll();
    }

    @Override
    public List<Evento> findByDescricao(String descricao) {
        return this.findByDescricao(descricao);
    }

    @Override
    public Optional<Evento> findById(Long id) {
        return this.eventoRepository.findById(id);
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
    public List<Evento> findByOrigem(String origem, Pageable pageable) {
        return this.eventoRepository.findByOrigem(origem);
    }

    @Override
    public List<Evento> findByLevel(Level level) {
        return this.eventoRepository.findByLevel(level);
    }
}
