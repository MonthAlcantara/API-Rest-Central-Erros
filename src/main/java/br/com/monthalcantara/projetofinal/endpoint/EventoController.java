package br.com.monthalcantara.projetofinal.endpoint;

import br.com.monthalcantara.projetofinal.dto.EventoDTO;
import br.com.monthalcantara.projetofinal.entity.Evento;
import br.com.monthalcantara.projetofinal.enums.Level;
import br.com.monthalcantara.projetofinal.service.interfaces.EventoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @GetMapping
    @ApiOperation("Busca todos Eventos")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Eventos não localizados"),
            @ApiResponse(code = 200, message = "Eventos localizados")})
    public ResponseEntity<Iterable<EventoDTO>> findAll(@PathParam("origem") String origem, Pageable pageable) {
        if (origem != null) {
            return new ResponseEntity<>(this.eventoService.findByOrigem(origem, pageable), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(this.eventoService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation("Busca um Evento pelo ID")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Evento não localizado"),
            @ApiResponse(code = 200, message = "Evento localizado")})
    public ResponseEntity<EventoDTO> findById(@PathVariable("id") Long id) {
        EventoDTO eventoDTO = this.eventoService.findById(id);
        if (eventoDTO == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(eventoDTO, HttpStatus.OK);
    }

    @GetMapping("/descricao/{descricao}")
    @ApiOperation("Busca um Evento pela Descrição")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Evento não localizado"),
            @ApiResponse(code = 200, message = "Evento localizado")})
    public ResponseEntity<List<EventoDTO>> findByDescricao(@PathVariable("descricao") String descricao) {
        return new ResponseEntity<>(this.eventoService.findByDescricao(descricao), HttpStatus.OK);
    }

    @GetMapping("/level/{level}")
    @ApiOperation("Busca um Evento pelo Level")
    public ResponseEntity<List<EventoDTO>> findByLevel(@PathVariable("level") Level level) {
        return new ResponseEntity<>(this.eventoService.findByLevel(level), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation("Cria um novo Evento")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Evento criado com sucesso")})
    public ResponseEntity<Evento> create(@Valid @RequestBody Evento evento) {
        return new ResponseEntity<>(this.eventoService.save(evento), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Exclui um Evento")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Evento não localizado"),
            @ApiResponse(code = 200, message = "Evento localizado"),
            @ApiResponse(code = 201, message = "Evento deletado com sucesso")})
    public void deleteById(@PathVariable("id") Long id) {
        this.eventoService.deleteById(id);
    }

}
