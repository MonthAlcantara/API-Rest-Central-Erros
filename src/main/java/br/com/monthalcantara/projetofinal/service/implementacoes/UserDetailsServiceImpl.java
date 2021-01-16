package br.com.monthalcantara.projetofinal.service.implementacoes;

import br.com.monthalcantara.projetofinal.exception.SenhaInvalidaException;
import br.com.monthalcantara.projetofinal.model.Usuario;
import br.com.monthalcantara.projetofinal.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@Profile({"prod", "test"})
public class UserDetailsServiceImpl implements UserDetailsService {

    private UsuarioRepository usuarioRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        String[] roles = usuario.isAdmin() ?
                new String[]{"USER", "ADMIN"} :
                new String[]{"USER"};

        return User.builder()
                .username(usuario.getLogin())
                .password(usuario.getPassword())
                .roles(roles)
                .build();
    }

    public UserDetails autenticar(Usuario usuario) {
        UserDetails userDetails = loadUserByUsername(usuario.getLogin());
        boolean isEquals = passwordEncoder.matches(usuario.getPassword(), userDetails.getPassword());
        if (isEquals) {
            return userDetails;
        }
        throw new SenhaInvalidaException();
    }
}
