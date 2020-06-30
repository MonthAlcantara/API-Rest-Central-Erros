package br.com.monthalcantara.projetofinal.repository;

import br.com.monthalcantara.projetofinal.entity.Usuario;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long> {
    Usuario findByLogin(String login);






}
