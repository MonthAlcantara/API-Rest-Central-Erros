package br.com.monthalcantara.projetofinal.endpoint;

import br.com.monthalcantara.projetofinal.entity.Evento;
import br.com.monthalcantara.projetofinal.enums.Level;
import br.com.monthalcantara.projetofinal.service.interfaces.EventoService;
import com.sun.xml.internal.ws.handler.HandlerException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/evento")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @PostMapping
    @ApiOperation("Cria um novo Evento")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Evento criado com sucesso")})
    public ResponseEntity<Evento> create(@Valid @RequestBody Evento Evento) {
        return new ResponseEntity<Evento>(this.eventoService.save(Evento), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Evento> update(@Valid @RequestBody Evento Evento) {
        return new ResponseEntity<Evento>(this.eventoService.save(Evento), HttpStatus.ACCEPTED);
    }

    @GetMapping
    @ApiOperation("Lista todos os Eventos")
    public Iterable<Evento> findAll(@PathParam("nome") String nome, Pageable pageable) {
        if (nome != null) {
            return this.eventoService.findByNome(nome.toString(), pageable);
        }
        return this.eventoService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Evento não localizado"), @ApiResponse(code = 200, message = "Evento localizado")})
    public ResponseEntity<Evento> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<Evento>(this.eventoService.findById(id)
                .orElseThrow(() -> new HandlerException("Evento")), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        this.eventoService.deleteById(id);
    }

    @GetMapping("/byDescricao/{descricao}")
    public List<Evento> findByCategoria(@PathVariable("descricao") String descricao) {
        return this.eventoService.findByDescricao(descricao);
    }

    @GetMapping("/byLevel/{level")
    public List<Evento> findByLevel(@PathVariable("level") Level level) {
        return this.eventoService.findByLevel(level);
    }



}
