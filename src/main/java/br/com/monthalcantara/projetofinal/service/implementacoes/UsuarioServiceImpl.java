package br.com.monthalcantara.projetofinal.service.implementacoes;

import br.com.monthalcantara.projetofinal.entity.Usuario;
import br.com.monthalcantara.projetofinal.repositories.UsuarioRepository;
import br.com.monthalcantara.projetofinal.service.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UserDetailsService, UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return this.usuarioRepository.findByLogin(login);
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Usuario> findByLogin(String login) {
        return Optional.empty();
    }

    @Override
    public Usuario save(Usuario novoUsuario) {
        return this.usuarioRepository.save(novoUsuario);
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<Usuario> findAll() {
        return this.usuarioRepository.findAll();
    }


    public Usuario updateUsuario(Long id, Usuario user) {
        Optional<Usuario> userInfo = this.usuarioRepository.findById(id);

        if (userInfo.isPresent()) {

            userInfo.get().setPassword(user.getPassword());
            userInfo.get().setLogin(user.getLogin());

            return this.usuarioRepository.save(userInfo.get());

        }
        return null;

    }

}
