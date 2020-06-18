package br.com.monthalcantara.projetofinal.endpoint;

import br.com.monthalcantara.projetofinal.entity.Usuario;
import br.com.monthalcantara.projetofinal.service.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService userService;

    @PostMapping
    public ResponseEntity<Usuario> create(@Valid @RequestBody Usuario usuario){
        return new ResponseEntity<>(this.userService.save(usuario), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id){
        this.userService.deleteById(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable("id") Long id){
        return new ResponseEntity<>(this.userService.findById(id).get(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Usuario> update(@Valid @RequestBody Usuario usuario) {
        return new ResponseEntity<>(this.userService.save(usuario), HttpStatus.ACCEPTED);
    }
}
