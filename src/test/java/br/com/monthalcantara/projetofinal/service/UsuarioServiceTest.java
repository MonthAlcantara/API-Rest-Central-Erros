package br.com.monthalcantara.projetofinal.service;

import br.com.monthalcantara.projetofinal.dto.UsuarioDTO;
import br.com.monthalcantara.projetofinal.exception.RecursoNotFound;
import br.com.monthalcantara.projetofinal.exception.RegraNegocioException;
import br.com.monthalcantara.projetofinal.model.Usuario;
import br.com.monthalcantara.projetofinal.service.interfaces.UsuarioService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UsuarioServiceTest {
    Usuario usuario, usuarioSalvo;

    @Autowired
    UsuarioService usuarioService;

    @MockBean
    Pageable pageable;

    @Test
    @DisplayName("Deve carregar usuário pelo username")
    void deveCarregarUsuarioPeloUsername() {
        usuario = Usuario.builder()
                .login("Teste 01")
                .password("123")
                .admin(true)
                .id(1L)
                .build();

        usuarioService.save(usuario);

        UserDetails userDetails = usuarioService.loadUserByUsername(usuario.getLogin());
        assertThat(userDetails.getUsername()).isEqualTo(usuario.getLogin());
        assertThat(userDetails.getPassword()).isEqualTo(usuario.getPassword());

    }

    @Test
    @DisplayName("Deve lançar erro ao não encontrar Login")
    void deveLancarErroLoginInvalido() {

        RuntimeException runtimeException = assertThrows(UsernameNotFoundException.class, () ->
                usuarioService.loadUserByUsername("login Inválido"));
        assertThat(runtimeException.getMessage()).contains("Usuário não encontrado");
    }

   // @Test
   // @DisplayName("Deve autenticar usuario")
   // @Disabled
    void deveAutenticarUsuario() {
        usuario = Usuario.builder()
                .login("Teste 02")
                .password("123")
                .admin(true)
                .id(1L)
                .build();
        usuarioService.save(usuario);
        UserDetails userDetails = usuarioService.autenticar(usuario);
        assertThat(userDetails).isNotNull();
    }

    @Test
    @DisplayName("Deve criar um novo usuário")
    void deveCriarUsuario() {
        usuario = Usuario.builder()
                .login("Teste 03")
                .password("123")
                .admin(true)
                .id(1L)
                .build();
        usuarioSalvo = usuarioService.save(usuario);

        Assertions.assertEquals(usuario, usuarioSalvo);
    }

    @Test
    @DisplayName("Deve lançar erro se Login ja existe")
    void naoDeveCriarUsuarioLoginRepetido() {
        usuario = Usuario.builder()
                .login("Teste 04")
                .password("123")
                .admin(true)
                .id(1L)
                .build();
        usuarioService.save(usuario);
        RuntimeException runtimeException = Assertions
                .assertThrows(RegraNegocioException.class, () -> usuarioService.save(usuario));

        Assertions
                .assertTrue(runtimeException.getMessage().contains("Ja existe um usuário com esse login. Por favor escolha outro"));
    }

    @Test
    @DisplayName("Deve buscar um usuario pelo ID")
    void deveBuscarUsuarioPeloId() {
        usuario = Usuario.builder()
                .login("Teste 05")
                .password("123")
                .admin(true)
                .id(1L)
                .build();
        usuarioService.save(usuario);
        UsuarioDTO usuarioEncontrado = usuarioService.findById(usuario.getId());

        assertThat(usuarioEncontrado).isNotNull();
    }

    @Test
    @DisplayName("Deve deletar Usuario pelo id")
    void deveDeletarPeloId() {
        usuario = Usuario.builder()
                .login("Teste 06")
                .password("123")
                .admin(true)
                .id(1L)
                .build();
        usuarioService.save(usuario);
        usuarioService.deleteById(usuario.getId());
        RuntimeException runtimeException = assertThrows(RecursoNotFound.class, () ->
                usuarioService.findById(usuario.getId()));

        assertTrue(runtimeException.getMessage()
                .contains("Id de Usuário não encontrado"));

    }

    @Test
    @DisplayName("Deve atualizar um Usuario")
    void deveAtualizarUmUsuario() {
        usuario = Usuario.builder()
                .login("Teste 07")
                .password("123")
                .admin(true)
                .id(1L)
                .build();
        Usuario usuarioModificado = usuarioService.save(usuario);

        usuarioModificado.setLogin("Login Modificado");
        usuarioModificado.setPassword("321");
        usuarioModificado.setAdmin(false);

        usuario.setLogin("Usuario Modificado");
        usuarioService.updateUsuario(usuario.getId(), usuarioModificado);

        assertThat(usuario.getId()).isEqualTo(usuarioModificado.getId());
        assertThat(usuario.getLogin()).isNotEqualTo(usuarioModificado.getLogin());
        assertThat(usuario.getPassword()).isNotEqualTo(usuarioModificado.getPassword());
        assertThat(usuario.isAdmin()).isNotEqualTo(usuarioModificado.isAdmin());
    }

    @Test
    @DisplayName("Deve buscar pelo login")
    void deveBuscarPeloLogin() {
        usuario = Usuario.builder()
                .login("Teste 08")
                .password("123")
                .admin(true)
                .id(1L)
                .build();
        usuarioService.save(usuario);
        assertThat(usuario.getLogin())
                .isEqualTo(usuarioService.findByLogin(usuario.getLogin()).getLogin());
    }

    @Test
    @DisplayName("Deve retornar true se já existe login cadastrado")
    void deveRetornarSeExisteLogin() {

        usuario = Usuario.builder()
                .login("Teste 09")
                .password("123")
                .admin(true)
                .id(1L)
                .build();

        usuarioService.save(usuario);

        Assertions.assertTrue(usuarioService.existsByLogin(usuario.getLogin()));
    }
}
