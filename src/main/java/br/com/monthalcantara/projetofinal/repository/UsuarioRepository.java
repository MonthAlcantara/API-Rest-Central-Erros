package br.com.monthalcantara.projetofinal.repository;

import br.com.monthalcantara.projetofinal.entity.Usuario;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long> {
    Optional<Usuario> findByLogin(String login);






}
