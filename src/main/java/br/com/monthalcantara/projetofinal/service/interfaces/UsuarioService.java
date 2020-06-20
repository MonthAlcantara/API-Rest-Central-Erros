package br.com.monthalcantara.projetofinal.service.interfaces;

import br.com.monthalcantara.projetofinal.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    Optional<Usuario> findById(Long id);
    Optional<Usuario> findByLogin(String login);
    Usuario save(Usuario novoUsuario);
    void deleteById(Long id);
    List<Usuario> findAll();
}
