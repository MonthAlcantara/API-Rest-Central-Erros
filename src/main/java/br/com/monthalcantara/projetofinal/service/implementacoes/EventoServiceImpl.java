package br.com.monthalcantara.projetofinal.service.implementacoes;

import br.com.monthalcantara.projetofinal.entity.Evento;
import br.com.monthalcantara.projetofinal.enums.Level;
import br.com.monthalcantara.projetofinal.service.interfaces.EventoService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventoServiceImpl implements EventoService {
    @Override
    public List<Evento> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Evento> findByDescricao(String descricao) {
        return null;
    }

    @Override
    public Optional<Evento> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Evento save(Evento Evento) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<Evento> findByNome(String nome, Pageable pageable) {
        return null;
    }

    @Override
    public List<Evento> findByLevel(Level level) {
        return null;
    }
}
