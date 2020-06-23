package br.com.monthalcantara.projetofinal.service.interfaces;

import br.com.monthalcantara.projetofinal.dto.UsuarioDTO;
import br.com.monthalcantara.projetofinal.entity.Usuario;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UsuarioService {
    UsuarioDTO findById(Long id);

    UsuarioDTO findByLogin(String login);

    Usuario save(Usuario novoUsuario);

    void deleteById(Long id);

    List<UsuarioDTO> findAll(Pageable pageable);

    Usuario updateUsuario(Long id,Usuario user);
}
