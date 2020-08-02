package br.com.monthalcantara.projetofinal;

import br.com.monthalcantara.projetofinal.dto.UsuarioDTO;
import br.com.monthalcantara.projetofinal.exception.RegraNegocioException;
import br.com.monthalcantara.projetofinal.model.Usuario;
import br.com.monthalcantara.projetofinal.service.interfaces.UsuarioService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class UsuarioRepositoryTests {

    Usuario usuario, save;

    @Autowired
    UsuarioService usuarioService;

    @BeforeEach
    @DisplayName("Carregamento de Contexto para os testes")
    void contextLoads() {
        usuario = Usuario.builder()
                .login("Teste")
                .password("123")
                .admin(true)
                .id(1L)
                .build();
        save = usuarioService.save(usuario);
    }


    @Test
    @DisplayName("Deve criar um novo usuário")
    public void deveCriarUsuario() {

        Assertions.assertEquals(usuario, save);
    }

    @Test
    @DisplayName("Deve buscar um usuario pelo ID")
    public void deveBuscarUsuarioPeloId() {

        UsuarioDTO Usersave = usuarioService.findById(usuario.getId());
        assertThat(Usersave).isNotNull();
    }

    @Test
    @DisplayName("Deve deletar Usuario pelo id")

    public void deveDeletarPeloId() {

        usuarioService.deleteById(usuario.getId());
        RuntimeException runtimeException = assertThrows(RegraNegocioException.class, () ->
                usuarioService.findById(usuario.getId()));

        assertTrue(runtimeException.getMessage()
                .contains("Id de Usuário não encontrado"));

    }

    @Test
    @DisplayName("Deve atualizar um Usuario")
    public void deveAtualizarUmUsuario() {
        usuario.setLogin("Usuario Modificado");
        Usuario usuarioModificado = usuarioService.save(usuario);

        assertThat(usuario.getId())
                .isEqualTo(usuarioModificado.getId());
    }

}
