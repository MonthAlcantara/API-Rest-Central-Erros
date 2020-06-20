package br.com.monthalcantara.projetofinal.endpoint;

import br.com.monthalcantara.projetofinal.entity.Usuario;
import br.com.monthalcantara.projetofinal.service.interfaces.UsuarioService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Usuarios não localizados"),
            @ApiResponse(code = 200, message = "Usuarios localizados")})
    public ResponseEntity<List<Usuario>> findAll() {
        return new ResponseEntity<>(this.userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation("Busca um usuário pelo Id")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Usuario não localizado"),
            @ApiResponse(code = 200, message = "Usuario localizado")})
    public ResponseEntity<Usuario> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(this.userService.findById(id).get(), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation("Cria um novo Usuário")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Não foi possível criar o Usuario"),
            @ApiResponse(code = 200, message = "Usuario criado"),
            @ApiResponse(code = 201, message = "Usuario criado com sucesso")})
    public ResponseEntity<Usuario> create(@Valid @RequestBody Usuario usuario) {
        return new ResponseEntity<>(this.userService.save(usuario), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Exclui um Usuário")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Usuario não localizado"),
            @ApiResponse(code = 200, message = "Usuario localizado"),
            @ApiResponse(code = 201, message = "Usuario deletado com sucesso")})
    public void deleteById(@PathVariable("id") Long id) {
        this.userService.deleteById(id);
    }


    @PutMapping
    @ApiOperation("Atualiza um Usuário")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Usuario não localizado"),
            @ApiResponse(code = 200, message = "Usuario localizado"),
            @ApiResponse(code = 201, message = "Usuario atualizado com sucesso")})
    public ResponseEntity<Usuario> update(@Valid @RequestBody Usuario usuario) {
        return new ResponseEntity<>(this.userService.save(usuario), HttpStatus.ACCEPTED);
    }
}
