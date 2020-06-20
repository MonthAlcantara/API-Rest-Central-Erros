package br.com.monthalcantara.projetofinal.endpoint;

import br.com.monthalcantara.projetofinal.entity.Usuario;
import br.com.monthalcantara.projetofinal.service.interfaces.UsuarioService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService userService;

    @GetMapping
    @ApiOperation("Busca todos os Usuários")
    public ResponseEntity<List<Usuario>> findAll() {
        return new ResponseEntity<>(this.userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation("Busca um usuário pelo Id")
    public ResponseEntity<Usuario> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(this.userService.findById(id).get(), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation("Cria um novo Usuário")
    public ResponseEntity<Usuario> create(@Valid @RequestBody Usuario usuario) {
        return new ResponseEntity<>(this.userService.save(usuario), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Exclui um Usuário")
    public void deleteById(@PathVariable("id") Long id) {
        this.userService.deleteById(id);
    }


    @PutMapping
    @ApiOperation("Atualiza um Usuário")
    public ResponseEntity<Usuario> update(@Valid @RequestBody Usuario usuario) {
        return new ResponseEntity<>(this.userService.save(usuario), HttpStatus.ACCEPTED);
    }
}
