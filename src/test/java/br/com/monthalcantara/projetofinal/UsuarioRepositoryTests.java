package br.com.monthalcantara.projetofinal;

import br.com.monthalcantara.projetofinal.dto.UsuarioDTO;
import br.com.monthalcantara.projetofinal.exception.RegraNegocioException;
import br.com.monthalcantara.projetofinal.model.Usuario;
import br.com.monthalcantara.projetofinal.service.interfaces.UsuarioService;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.platform.runner.JUnitPlatform;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


@RunWith(JUnitPlatform.class)
@SpringBootTest
class UsuarioRepositoryTests {

    Usuario usuario, usuarioSalvo;

    @Autowired
    UsuarioService usuarioService;

    @BeforeEach
    void contextLoads() {
        usuario = Usuario.builder()
                .login("Teste")
                .password("123")
                .admin(true)
                .id(1L)
                .build();
        usuarioSalvo = usuarioService.save(usuario);
    }

    @Test
    @DisplayName("Deve carregar usuário pelo username")
    void deveCarregarUsuarioPeloUsername(){
        UserDetails userDetails = usuarioService.loadUserByUsername(usuario.getLogin());
        assertThat(userDetails.getUsername()).isEqualTo(usuario.getLogin());
        assertThat(userDetails.getPassword()).isEqualTo(usuario.getPassword());
    }

    @Test
    @DisplayName("Deve lançar erro ao não encontrar Login")
    void deveLancarErroLoginInvalido(){
        RuntimeException runtimeException = assertThrows(UsernameNotFoundException.class, () ->
                usuarioService.loadUserByUsername("login Inválido"));
        assertThat(runtimeException.getMessage()).contains("Usuário não encontrado");
    }

    @Test
    @DisplayName("Deve autenticar usuario")
    @Disabled
    void deveAutenticarUsuario(){
        assertThat(usuarioService.autenticar(usuario)).isNotNull();
    }

    @Test
    @DisplayName("Deve criar um novo usuário")
    void deveCriarUsuario() {

        Assertions.assertEquals(usuario, usuarioSalvo);
    }

    @Test
    @DisplayName("Deve buscar um usuario pelo ID")
    void deveBuscarUsuarioPeloId() {

        UsuarioDTO usuarioEncontrado = usuarioService.findById(usuario.getId());
        assertThat(usuarioEncontrado).isNotNull();
    }

    @Test
    @DisplayName("Deve deletar Usuario pelo id")
    void deveDeletarPeloId() {

        usuarioService.deleteById(usuario.getId());
        RuntimeException runtimeException = assertThrows(RegraNegocioException.class, () ->
                usuarioService.findById(usuario.getId()));

        assertTrue(runtimeException.getMessage()
                .contains("Id de Usuário não encontrado"));

    }

    @Test
    @DisplayName("Deve atualizar um Usuario")
    void deveAtualizarUmUsuario() {
        usuario.setLogin("Usuario Modificado");
        Usuario usuarioModificado = usuarioService.updateUsuario(usuario.getId(), usuario);

        assertThat(usuario.getId())
                .isEqualTo(usuarioModificado.getId());
    }

    @Test
    @DisplayName("Deve buscar pelo login")
    void deveBuscarPeloLogin() {

        assertThat(usuario.getLogin())
                .isEqualTo(usuarioService.findByLogin(usuario.getLogin()).getLogin());
    }

}
