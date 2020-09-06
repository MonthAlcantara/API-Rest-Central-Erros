package br.com.monthalcantara.projetofinal.configuration;

import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

public class ServletContainerInitializer extends AbstractHttpSessionApplicationInitializer {

    public ServletContainerInitializer() {
        super(SessionConfig.class);
    }
}
