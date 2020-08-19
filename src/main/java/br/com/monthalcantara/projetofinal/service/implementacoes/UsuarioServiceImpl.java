package br.com.monthalcantara.projetofinal.service.implementacoes;

import br.com.monthalcantara.projetofinal.dto.UsuarioDTO;
import br.com.monthalcantara.projetofinal.exception.RecursoNotFound;
import br.com.monthalcantara.projetofinal.model.Usuario;
import br.com.monthalcantara.projetofinal.exception.RegraNegocioException;
import br.com.monthalcantara.projetofinal.exception.SenhaInvalidaException;
import br.com.monthalcantara.projetofinal.repository.UsuarioRepository;
import br.com.monthalcantara.projetofinal.service.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UserDetailsService, UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserDetails autenticar(Usuario usuario) {
        UserDetails userDetails = loadUserByUsername(usuario.getLogin());
        boolean isEquals = passwordEncoder.matches(usuario.getPassword(), userDetails.getPassword());
        if (isEquals) {
            return userDetails;
        }
        throw new SenhaInvalidaException();
    }

    @Override
    public boolean existsByLogin(String login) {
        return usuarioRepository.existsByLogin(login);
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


    @Override
    public UsuarioDTO findById(Long id) {
        Optional<Usuario> usuario = this.usuarioRepository.findById(id);
        return usuario
                .map(UsuarioDTO::new)
                .orElseThrow(() -> new RecursoNotFound("Id de Usuário não encontrado"));
    }

    @Override
    public UsuarioDTO findByLogin(String login) {
        return new UsuarioDTO(this.usuarioRepository
                .findByLogin(login)
                .orElseThrow(() -> new RecursoNotFound("Login de Usuário não encontrado")));

    }

    @Override
    public Usuario save(Usuario novoUsuario) {
        if (existsByLogin(novoUsuario.getLogin())) {
        throw new RegraNegocioException("Ja existe um usuário com esse login. Por favor escolha outro");
        }
        novoUsuario.setPassword(passwordEncoder.encode(novoUsuario.getPassword()));
        return this.usuarioRepository.save(novoUsuario);
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        this.usuarioRepository.deleteById(id);
    }

    @Override
    public List<UsuarioDTO> findAll(Pageable pageable) {
        List<Usuario> listaUsuario = (List<Usuario>) this.usuarioRepository.findAll();
        List<UsuarioDTO> listaUsuarioDTO = new ArrayList<>();

        for (Usuario usuario : listaUsuario) {
            listaUsuarioDTO.add(new UsuarioDTO(usuario));
        }
        return listaUsuarioDTO;
    }

    public Usuario updateUsuario(Long id, Usuario user) {
        Optional<Usuario> userInfo = this.usuarioRepository.findById(id);

        return userInfo.map(u -> {
            u.setPassword(u.getPassword());
            u.setLogin(u.getLogin());
            return this.usuarioRepository.save(u);
        }).orElseThrow(() -> new RecursoNotFound("Usuario não encontrado"));

    }

}
