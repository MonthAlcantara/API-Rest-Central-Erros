package br.com.monthalcantara.projetofinal.repositories;

import br.com.monthalcantara.projetofinal.entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuariosRepository extends JpaRepository<Usuarios,Long> {
    Usuarios findByLogin(String login);
}
