package br.com.monthalcantara.projetofinal.service.implementacoes;

import br.com.monthalcantara.projetofinal.dto.UsuarioDTO;
import br.com.monthalcantara.projetofinal.entity.Usuario;
import br.com.monthalcantara.projetofinal.repositories.UsuarioRepository;
import br.com.monthalcantara.projetofinal.service.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        //return this.usuarioRepository.findByLogin(login);
        Usuario user = usuarioRepository.findByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException(login);
        }
        return user;
    }


    @Override
    public UsuarioDTO findById(Long id) {
        Optional<Usuario> usuario = this.usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            return new UsuarioDTO(usuario.get());
        }
        return null;
    }

    @Override
    public UsuarioDTO findByLogin(String login) {
        return new UsuarioDTO(this.usuarioRepository.findByLogin(login));

    }

    @Override
    public Usuario save(Usuario novoUsuario) {
        return this.usuarioRepository.save(novoUsuario);
    }

    @Override
    public void deleteById(Long id) {
        this.usuarioRepository.deleteById(id);
    }

    @Override
    public List<UsuarioDTO> findAll(Pageable pageable) {
        List<Usuario> listaUsuario = this.usuarioRepository.findAll();
        List<UsuarioDTO> listaUsuarioDTO = new ArrayList<>();

        for (Usuario usuario : listaUsuario) {
            listaUsuarioDTO.add(new UsuarioDTO(usuario));
        }
        return listaUsuarioDTO;
    }

    public Usuario updateUsuario(Long id, Usuario user) {
        Optional<Usuario> userInfo = this.usuarioRepository.findById(id);

        if (userInfo.isPresent()) {

            userInfo.get().setPassword(user.getPassword());
            userInfo.get().setLogin(user.getLogin());

            return this.usuarioRepository.save(userInfo.get());

        }
        return save(user);

    }

}
