package br.com.monthalcantara.projetofinal.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Profile({"prod","test","dev"})
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 360)
public class SessionConfig{
    @Bean
    public LettuceConnectionFactory connectionFactory() {
        return new LettuceConnectionFactory();
    }


}
