package br.com.monthalcantara.projetofinal.service;

import br.com.monthalcantara.projetofinal.dto.UsuarioDTO;
import br.com.monthalcantara.projetofinal.exception.RecursoNotFound;
import br.com.monthalcantara.projetofinal.exception.RegraNegocioException;
import br.com.monthalcantara.projetofinal.model.Usuario;
import br.com.monthalcantara.projetofinal.repository.UsuarioRepository;
import br.com.monthalcantara.projetofinal.service.implementacoes.UsuarioServiceImpl;
import br.com.monthalcantara.projetofinal.service.interfaces.UsuarioService;
import br.com.monthalcantara.projetofinal.util.UsuarioFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class UsuarioServiceTest {

    private Usuario usuario, usuarioSalvo, usuarioNaoSalvo;

    private UsuarioService usuarioService;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    private Pageable pageable;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        pageable = PageRequest.of(1, 1);
        usuarioService = new UsuarioServiceImpl(usuarioRepository, passwordEncoder);

        usuarioNaoSalvo = UsuarioFactory.geraAdminNaoSalvo();
        usuarioSalvo = UsuarioFactory.geraAdminSalvo();
    }

    @Test
    @DisplayName("Deve criar um novo usuário")
    void deveCriarUsuario() {
        Mockito.when(usuarioRepository.save(usuarioNaoSalvo)).thenReturn(usuarioSalvo);
        usuario = usuarioService.save(usuarioNaoSalvo);

        Assertions.assertEquals(usuario, usuarioSalvo);
        Assertions.assertNotNull(usuario);
    }

    @Test
    @DisplayName("Deve lançar erro se Login ja existe")
    void naoDeveCriarUsuarioLoginRepetido() {
        Mockito.when(usuarioRepository.existsByLogin(usuarioNaoSalvo.getLogin())).thenReturn(true);
        try {
            usuarioService.save(usuarioNaoSalvo);
        } catch (RegraNegocioException e) {

        } finally {
            RuntimeException runtimeException = Assertions
                    .assertThrows(RegraNegocioException.class, () -> usuarioService.save(usuarioNaoSalvo));

            Assertions
                    .assertTrue(runtimeException.getMessage().contains("Ja existe um usuário com esse login. Por favor escolha outro"));

        }
    }

    @Test
    @DisplayName("Deve buscar um usuario pelo ID")
    void deveBuscarUsuarioPeloId() {

        Mockito.when(usuarioRepository.findById(usuarioSalvo.getId())).thenReturn(Optional.of(usuarioSalvo));
        UsuarioDTO usuarioEncontrado = usuarioService.findById(usuarioSalvo.getId());

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
