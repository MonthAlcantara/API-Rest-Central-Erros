package br.com.monthalcantara.projetofinal.repositories;

import br.com.monthalcantara.projetofinal.entity.Eventos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventosRepository extends JpaRepository<Eventos,Long> {
}
