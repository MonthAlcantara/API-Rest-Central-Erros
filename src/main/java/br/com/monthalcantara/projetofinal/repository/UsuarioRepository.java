package br.com.monthalcantara.projetofinal.repository;

import br.com.monthalcantara.projetofinal.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByLogin(String login);

    Boolean existsByLogin(String login);


}
