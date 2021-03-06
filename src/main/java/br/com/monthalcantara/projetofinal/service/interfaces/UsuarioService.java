package br.com.monthalcantara.projetofinal.service.interfaces;

import br.com.monthalcantara.projetofinal.dto.UsuarioDTO;
import br.com.monthalcantara.projetofinal.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsuarioService {

    Usuario findById(Long id);

    Usuario findByLogin(String login);

    Usuario save(Usuario novoUsuario);

    void deleteById(Long id);

    Page<Usuario> findAll(Pageable pageable);

    Usuario updateUsuario(Long id, Usuario user);

    boolean existsByLogin(String login);

}
