package br.com.monthalcantara.projetofinal;

import br.com.monthalcantara.projetofinal.service.implementacoes.UsuarioServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ProjetofinalApplication {


	public static void main(String[] args) {
		SpringApplication.run(ProjetofinalApplication.class, args);
	}

}
