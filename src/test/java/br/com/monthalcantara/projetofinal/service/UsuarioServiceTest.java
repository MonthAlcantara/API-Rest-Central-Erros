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


    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        usuarioService = new UsuarioServiceImpl(usuarioRepository, passwordEncoder);

        usuario = new Usuario();
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
        Mockito.doNothing().when(usuarioRepository).deleteById(usuarioSalvo.getId());
        Mockito.when(usuarioRepository.findById(usuarioSalvo.getId())).thenReturn(Optional.of(usuarioSalvo));
        usuarioService.deleteById(usuarioSalvo.getId());

        Mockito.when(usuarioRepository.findById(usuarioSalvo.getId())).thenReturn(Optional.empty());
        RuntimeException runtimeException = assertThrows(RecursoNotFound.class, () ->
                usuarioService.findById(usuarioSalvo.getId()));

        assertTrue(runtimeException.getMessage()
                .contains("Id de Usuário não encontrado"));

    }

    @Test
    @DisplayName("Deve atualizar um Usuario")
    void deveAtualizarUmUsuario() {
        Mockito.when(usuarioRepository.findById(usuarioSalvo.getId()))
                .thenReturn(Optional.of(usuarioSalvo));

        usuario.setLogin("Login Modificado");
        usuario.setPassword("321");
        usuario.setAdmin(false);

        Mockito.when(usuarioRepository.save(usuarioSalvo)).thenReturn(usuario);
        usuarioService.updateUsuario(usuarioSalvo.getId(), usuario);

        assertThat(usuarioSalvo.getLogin()).isEqualTo(usuario.getLogin());
        assertThat(usuarioSalvo.getPassword()).isEqualTo(usuario.getPassword());
        assertThat(usuarioSalvo.isAdmin()).isNotEqualTo(usuario.isAdmin());
    }

    @Test
    @DisplayName("Deve buscar pelo login")
    void deveBuscarPeloLogin() {
        Mockito.when(usuarioRepository.findByLogin(usuarioSalvo.getLogin())).thenReturn(Optional.of(usuarioSalvo));
        assertThat(usuarioSalvo.getLogin())
                .isEqualTo(usuarioService.findByLogin(usuarioSalvo.getLogin()).getLogin());
    }

    @Test
    @DisplayName("Deve retornar true se já existe login cadastrado")
    void deveRetornarSeExisteLogin() {

        Mockito.when(usuarioRepository.existsByLogin(usuarioSalvo.getLogin())).thenReturn(true);

        Assertions.assertTrue(usuarioService.existsByLogin(usuarioSalvo.getLogin()));
    }
}
