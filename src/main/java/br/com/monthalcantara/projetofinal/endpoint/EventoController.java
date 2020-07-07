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
@RequestMapping("/v1/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @GetMapping("/protected")
    @ApiOperation("Busca todos Eventos")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Eventos não localizados"),
            @ApiResponse(code = 403, message = "Você não possui permissão para visualizar este recurso"),
            @ApiResponse(code = 401, message = "Você não possui credenciais de autenticação válidas"),
            @ApiResponse(code = 200, message = "Eventos localizados")})
    public ResponseEntity<Iterable<EventoDTO>> findAll(@PathParam("origem") String origem, Pageable pageable) {
        return new ResponseEntity<>(this.eventoService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/protected/{id}")
    @ApiOperation("Busca um Evento pelo ID")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Evento não localizado"),
            @ApiResponse(code = 403, message = "Você não possui permissão para visualizar este recurso"),
            @ApiResponse(code = 401, message = "Você não possui credenciais de autenticação válidas"),
            @ApiResponse(code = 200, message = "Evento localizado")})
    public ResponseEntity<EventoDTO> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(this.eventoService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/protected/descricao/{descricao}")
    @ApiOperation("Busca um Evento pela Descrição")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Evento não localizado"),
            @ApiResponse(code = 403, message = "Você não possui permissão para visualizar este recurso"),
            @ApiResponse(code = 401, message = "Você não possui credenciais de autenticação válidas"),
            @ApiResponse(code = 200, message = "Evento localizado")})
    public ResponseEntity<List<EventoDTO>> findByDescricao(@PathVariable("descricao") String descricao) {
        return new ResponseEntity<>(this.eventoService.findByDescricao(descricao), HttpStatus.OK);
    }

    @GetMapping("/protected/level/{level}")
    @ApiOperation("Busca um Evento pelo Level")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Evento não localizado"),
            @ApiResponse(code = 403, message = "Você não possui permissão para visualizar este recurso"),
            @ApiResponse(code = 401, message = "Você não possui credenciais de autenticação válidas"),
            @ApiResponse(code = 200, message = "Evento localizado")})
    public ResponseEntity<List<EventoDTO>> findByLevel(@PathVariable("level") Level level) {
        return new ResponseEntity<>(this.eventoService.findByLevel(level), HttpStatus.OK);
    }

    @PostMapping()
    @ApiOperation("Cria um novo Evento")
    @ApiResponses(value = {@ApiResponse(code = 403, message = "Você não possui permissão para visualizar este recurso"),
            @ApiResponse(code = 401, message = "Você não possui credenciais de autenticação válidas"),
            @ApiResponse(code = 201, message = "Evento criado com sucesso")})
    public ResponseEntity<Evento> create(@Valid @RequestBody Evento evento) {
        return new ResponseEntity<>(this.eventoService.save(evento), HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/{id}")
    @ApiOperation("Exclui um Evento")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Evento não localizado"),
            @ApiResponse(code = 403, message = "Você não possui permissão para visualizar este recurso"),
            @ApiResponse(code = 401, message = "Você não possui credenciais de autenticação válidas"),
            @ApiResponse(code = 200, message = "Evento localizado"),
            @ApiResponse(code = 201, message = "Evento deletado com sucesso")})
    public void deleteById(@PathVariable("id") Long id) {
        this.eventoService.deleteById(id);
    }

}
