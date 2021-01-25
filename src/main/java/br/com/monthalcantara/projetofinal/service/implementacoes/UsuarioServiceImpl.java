package br.com.monthalcantara.projetofinal.service.implementacoes;

import br.com.monthalcantara.projetofinal.exception.RecursoNotFound;
import br.com.monthalcantara.projetofinal.exception.RegraNegocioException;
import br.com.monthalcantara.projetofinal.model.Usuario;
import br.com.monthalcantara.projetofinal.repository.UsuarioRepository;
import br.com.monthalcantara.projetofinal.service.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private UsuarioRepository usuarioRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    @Transactional
    public boolean existsByLogin(String login) {
        return usuarioRepository.existsByLogin(login);
    }


    @Override
    @Transactional
    public Usuario findById(Long id) {
        Optional<Usuario> usuario = this.usuarioRepository.findById(id);
        return usuario
                .orElseThrow(() -> new RecursoNotFound("Id de Usuário não encontrado"));
    }
//    @Override
//    @Transactional
//    public UsuarioDTO findById(Long id) {
//        Optional<Usuario> usuario = this.usuarioRepository.findById(id);
//        return usuario
//                .map(UsuarioDTO::new)
//                .orElseThrow(() -> new RecursoNotFound("Id de Usuário não encontrado"));
//    }

    @Override
    @Transactional
    public Usuario findByLogin(String login) {
        return this.usuarioRepository
                .findByLogin(login)
                .orElseThrow(() -> new RecursoNotFound("Login de Usuário não encontrado"));

    }

    @Override
    @Transactional
    public Usuario save(Usuario novoUsuario) {
        if (existsByLogin(novoUsuario.getLogin())) {
        throw new RegraNegocioException("Ja existe um usuário com esse login. Por favor escolha outro");
        }
        novoUsuario.setPassword(passwordEncoder.encode(novoUsuario.getPassword()));
        return this.usuarioRepository.save(novoUsuario);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        findById(id);
        this.usuarioRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Page<Usuario> findAll(Pageable pageable) {
        Page<Usuario> paginaUsuarios = this.usuarioRepository.findAll(pageable);
        return paginaUsuarios;
    }

    @Override
    @Transactional
    public Usuario updateUsuario(Long id, Usuario user) {
        Optional<Usuario> userInfo = this.usuarioRepository.findById(id);

        return userInfo.map(u -> {
            u.setPassword(user.getPassword());
            u.setLogin(user.getLogin());
            return this.usuarioRepository.save(u);
        }).orElseThrow(() -> new RecursoNotFound("Usuario não encontrado"));

    }

}
