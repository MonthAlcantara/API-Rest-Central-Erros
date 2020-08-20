package br.com.monthalcantara.projetofinal.repository;

import br.com.monthalcantara.projetofinal.model.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UsuarioRepositoryTests {

    Usuario usuario, usuarioSalvo;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Deve salvar um usuario no banco de dados")
    void deveCriarUsuario() {
        usuario = geradorDeUsuario();
        usuarioSalvo = usuarioRepository.save(usuario);

        Assertions.assertNotNull(usuarioSalvo);
    }

    @Test
    @DisplayName("Deve buscar um usuario pelo ID")
    void deveBuscarUsuarioPeloId() {
        usuario = geradorDeUsuario();
        usuarioRepository.save(usuario);
        assertThat(usuarioRepository.findById(usuario.getId())).isPresent();


    }

    @Test
    @DisplayName("Deve deletar usuario pelo id")
    void deveDeletarPeloId() {
        usuario = geradorDeUsuario();
        usuarioRepository.save(usuario);
        usuarioRepository.delete(usuario);
        assertThat(usuarioRepository.findById(usuario.getId())).isNotPresent();


    }

    @Test
    @DisplayName("Deve atualizar um usuario")
    void deveAtualizarUmUsuario() {
        usuario = geradorDeUsuario();
        Usuario usuarioModificado = usuarioRepository.save(usuario);

        usuarioModificado.setLogin("Login Modificado");
        usuarioModificado.setPassword("321");
        usuarioModificado.setAdmin(false);

        usuario.setLogin("Usuario Modificado");
        usuarioRepository.save(usuarioModificado);

        assertThat(usuario.getId()).isEqualTo(usuarioModificado.getId());
        assertThat(usuario.getLogin()).isNotEqualTo(usuarioModificado.getLogin());
        assertThat(usuario.getPassword()).isNotEqualTo(usuarioModificado.getPassword());
        assertThat(usuario.isAdmin()).isNotEqualTo(usuarioModificado.isAdmin());
    }

    @Test
    @DisplayName("Deve buscar um usuario pelo login")
    void deveBuscarPeloLogin() {
        usuario = geradorDeUsuario();;
        usuarioRepository.save(usuario);
        assertThat(usuario)
                .isEqualTo(usuarioRepository.findByLogin(usuario.getLogin()).get());
    }

    @Test
    @DisplayName("Deve retornar true se já existe login cadastrado")
    void deveRetornarSeExisteLogin() {

        usuario = geradorDeUsuario();

        usuarioRepository.save(usuario);

        Assertions.assertTrue(usuarioRepository.existsByLogin(usuario.getLogin()));
    }

    private Usuario geradorDeUsuario(){
        return Usuario.builder()
                .login("Teste")
                .password("123")
                .admin(true)
                .id(1L)
                .build();
    }

}
