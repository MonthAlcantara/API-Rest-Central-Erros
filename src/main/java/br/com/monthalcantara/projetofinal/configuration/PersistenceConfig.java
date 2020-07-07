package br.com.monthalcantara.projetofinal.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableTransactionManagement
public class PersistenceConfig {

    @Bean
    public RestTemplate restemplate(){
        return new RestTemplate();
    }

}
