package br.com.monthalcantara.projetofinal.service.implementacoes;

import br.com.monthalcantara.projetofinal.model.Usuario;
import br.com.monthalcantara.projetofinal.repository.UsuarioRepository;
import br.com.monthalcantara.projetofinal.util.UsuarioFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserDetailsServiceImplTest {

    private Usuario usuario, usuarioNaoSalvo, usuarioSalvo;

    private UserDetailsServiceImpl usuarioService;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    private Pageable pageable;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        pageable = PageRequest.of(1, 1);
        usuario = UsuarioFactory.geraAdminNaoSalvo();
        usuarioService = new UserDetailsServiceImpl(usuarioRepository,passwordEncoder);

    }

    @Test
    @DisplayName("Deve carregar usuário pelo username")
    void deveCarregarUsuarioPeloUsername() {

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

     @Test
     @DisplayName("Deve autenticar usuario")
     @Disabled
    void deveAutenticarUsuario() {
         geradorUsuario();
         UserDetails userDetails = usuarioService.autenticar(usuario);
        assertThat(userDetails).isNotNull();
    }

    private Usuario geradorUsuario() {
      return Usuario.builder()
                .login("Teste 02")
                .password("123")
                .admin(true)
                .id(1L)
                .build();
    }


}