package br.com.monthalcantara.projetofinal.repository;

import br.com.monthalcantara.projetofinal.model.Usuario;
import br.com.monthalcantara.projetofinal.util.UsuarioFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest
class UsuarioRepositoryTests {

    private Usuario usuario;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    public void init() {
        usuario = UsuarioFactory.geraAdminNaoSalvo();
        usuarioRepository.save(usuario);
    }

    @Test
    @DisplayName("Deve salvar um usuario no banco de dados")
    void deveCriarUsuario() {
        Assertions.assertNotNull(usuario);
    }

    @Test
    @DisplayName("Deve buscar um usuario pelo ID")
    void deveBuscarUsuarioPeloId() {

        assertThat(usuarioRepository.findById(usuario.getId())).isPresent();


    }

    @Test
    @DisplayName("Deve deletar usuario pelo id")
    void deveDeletarPeloId() {

        usuarioRepository.delete(usuario);
        assertThat(usuarioRepository.findById(usuario.getId())).isNotPresent();


    }

    @Test
    @DisplayName("Deve atualizar um usuario")
    void deveAtualizarUmUsuario() {

        Usuario usuarioModificado = usuario;

        usuarioModificado.setLogin("Login Modificado");
        usuarioModificado.setPassword("321");
        usuarioModificado.setAdmin(false);

        usuarioRepository.save(usuarioModificado);

        assertThat(usuario.getId()).isEqualTo(usuarioModificado.getId());
        assertThat(usuario.getPassword()).isNotEqualTo(usuarioModificado.getPassword());
        assertThat(usuario.isAdmin()).isNotEqualTo(usuarioModificado.isAdmin());
    }

    @Test
    @DisplayName("Deve buscar um usuario pelo login")
    void deveBuscarPeloLogin() {

        assertThat(usuario)
                .isEqualTo(usuarioRepository.findByLogin(usuario.getLogin()).get());
    }

    @Test
    @DisplayName("Deve retornar true se já existe login cadastrado")
    void deveRetornarSeExisteLogin() {

        Assertions.assertTrue(usuarioRepository.existsByLogin(usuario.getLogin()));
    }


}
