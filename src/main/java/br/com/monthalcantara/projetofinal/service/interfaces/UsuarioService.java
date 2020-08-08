package br.com.monthalcantara.projetofinal.service.interfaces;

import br.com.monthalcantara.projetofinal.dto.UsuarioDTO;
import br.com.monthalcantara.projetofinal.model.Usuario;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UsuarioService extends UserDetailsService {

    UsuarioDTO findById(Long id);

    UsuarioDTO findByLogin(String login);

    Usuario save(Usuario novoUsuario);

    void deleteById(Long id);

    List<UsuarioDTO> findAll(Pageable pageable);

    Usuario updateUsuario(Long id,Usuario user);

    Usuario getUserInfoByUsuarioLogin(String login);

    UserDetails autenticar(Usuario usuario);

    boolean existsByLogin(String login);
}
