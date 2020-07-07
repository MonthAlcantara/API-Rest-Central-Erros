package br.com.monthalcantara.projetofinal.service.implementacoes;

import br.com.monthalcantara.projetofinal.dto.UsuarioDTO;
import br.com.monthalcantara.projetofinal.entity.Usuario;
import br.com.monthalcantara.projetofinal.error.RegraNegocioException;
import br.com.monthalcantara.projetofinal.repository.UsuarioRepository;
import br.com.monthalcantara.projetofinal.service.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UserDetailsService, UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado"));
        List<GrantedAuthority> authoritiesAdmin = AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN");
        List<GrantedAuthority> authoritiesUser = AuthorityUtils.createAuthorityList("ROLE_USER");
        return new User(usuario.getUsername(), usuario.getPassword(), usuario.isAdmin() ? authoritiesAdmin : authoritiesUser);
    }


    @Override
    public UsuarioDTO findById(Long id) {
        Optional<Usuario> usuario = this.usuarioRepository.findById(id);
        return usuario
                .map(UsuarioDTO::new)
                .orElseThrow(() -> new RegraNegocioException("Id de Usuário não encontrado"));
    }

    @Override
    public Usuario getUserInfoByUsuarioLogin(String login) {

        return usuarioRepository.findByLogin(login)
                .orElseThrow(() -> new RegraNegocioException("Login de Usuário não encontrado"));
    }

    @Override
    public UsuarioDTO findByLogin(String login) {
        return new UsuarioDTO(this.usuarioRepository
                .findByLogin(login)
                .orElseThrow(() -> new RegraNegocioException("Login de Usuário não encontrado")));

    }

    @Override
    public Usuario save(Usuario novoUsuario) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        novoUsuario.setPassword(bCryptPasswordEncoder.encode(novoUsuario.getPassword()));
        return this.usuarioRepository.save(novoUsuario);
    }

    @Override
    public void deleteById(Long id) {
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
        }).orElseThrow(() -> new RegraNegocioException("Usuario não encontrado"));

    }

}
