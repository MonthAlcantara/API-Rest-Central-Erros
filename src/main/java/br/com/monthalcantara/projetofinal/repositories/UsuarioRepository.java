package br.com.monthalcantara.projetofinal.repositories;

import br.com.monthalcantara.projetofinal.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByLogin(String login);




}
