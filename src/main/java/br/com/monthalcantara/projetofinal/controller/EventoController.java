package br.com.monthalcantara.projetofinal.controller;

import br.com.monthalcantara.projetofinal.enums.Level;
import br.com.monthalcantara.projetofinal.model.Evento;
import br.com.monthalcantara.projetofinal.service.interfaces.EventoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/v1/eventos")
public class EventoController {

    private EventoService eventoService;

    @Autowired
    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping("/protected")
    @ApiOperation("Busca todos Eventos")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Eventos não localizados"),
            @ApiResponse(code = 403, message = "Você não possui permissão para visualizar este recurso"),
            @ApiResponse(code = 401, message = "Você não possui credenciais de autenticação válidas"),
            @ApiResponse(code = 200, message = "Eventos localizados")})
    public ResponseEntity findAll(@PageableDefault(size = 5) Pageable pageable) {
        log.info("Buscando por todos os eventos cadastrados");
        return new ResponseEntity(this.eventoService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/protected/{id}")
    @ApiOperation("Busca um Evento pelo ID")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Evento não localizado"),
            @ApiResponse(code = 403, message = "Você não possui permissão para visualizar este recurso"),
            @ApiResponse(code = 401, message = "Você não possui credenciais de autenticação válidas"),
            @ApiResponse(code = 200, message = "Evento localizado")})
    public ResponseEntity findById(@PathVariable("id") Long id) {
        return new ResponseEntity(this.eventoService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/protected/descricao/{descricao}")
    @ApiOperation("Busca um Evento pela Descrição")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Evento não localizado"),
            @ApiResponse(code = 403, message = "Você não possui permissão para visualizar este recurso"),
            @ApiResponse(code = 401, message = "Você não possui credenciais de autenticação válidas"),
            @ApiResponse(code = 200, message = "Evento localizado")})
    public ResponseEntity findByDescricao(@PathVariable("descricao") String descricao, @PageableDefault(size = 5) Pageable pageable) {
        log.info("Buscando eventos cadastrados pela descrição: {} ", descricao);
        return new ResponseEntity(this.eventoService.findByDescricao(descricao, pageable), HttpStatus.OK);
    }

    @GetMapping("/protected/level/{level}")
    @ApiOperation("Busca um Evento pelo Level")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Evento não localizado"),
            @ApiResponse(code = 403, message = "Você não possui permissão para visualizar este recurso"),
            @ApiResponse(code = 401, message = "Você não possui credenciais de autenticação válidas"),
            @ApiResponse(code = 200, message = "Evento localizado")})
    public ResponseEntity findByLevel(@PathVariable("level") Level level, @PageableDefault(size = 5) Pageable pageable) {
        log.info("Buscando eventos cadastrados pela level: {} ", level);
        return new ResponseEntity(this.eventoService.findByLevel(level, pageable), HttpStatus.OK);
    }

    @PostMapping()
    @ApiOperation("Cria um novo Evento")
    @ApiResponses(value = {@ApiResponse(code = 403, message = "Você não possui permissão para visualizar este recurso"),
            @ApiResponse(code = 401, message = "Você não possui credenciais de autenticação válidas"),
            @ApiResponse(code = 201, message = "Evento criado com sucesso")})
    public ResponseEntity create(@Valid @RequestBody Evento evento) {
        log.info("Cadastrando um novo evento");
        return new ResponseEntity(this.eventoService.save(evento), HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/{id}")
    @ApiOperation("Exclui um Evento")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Evento não localizado"),
            @ApiResponse(code = 403, message = "Você não possui permissão para visualizar este recurso"),
            @ApiResponse(code = 401, message = "Você não possui credenciais de autenticação válidas"),
            @ApiResponse(code = 200, message = "Evento localizado"),
            @ApiResponse(code = 201, message = "Evento deletado com sucesso")})
    public void deleteById(@PathVariable("id") Long id) {
        log.info("Excluindo um evento cadastrado");
        this.eventoService.deleteById(id);
    }


}
