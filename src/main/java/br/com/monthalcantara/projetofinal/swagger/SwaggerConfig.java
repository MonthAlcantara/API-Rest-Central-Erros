package br.com.monthalcantara.projetofinal.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket productApi() {
        List<SecurityScheme> auth = new ArrayList<>();
        auth.add(new BasicAuth("bearer"));
        return new Docket(DocumentationType.SWAGGER_2)
                .securitySchemes(auth)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.monthalcantara.projetofinal"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaInfo());

    }


    private ApiInfo metaInfo() {

        return new ApiInfo(
                "Central de erros API REST",
                "API REST Logs.",
                "1.0",
                "Terms of Service",
                new Contact("Montival Junior", "https://monthalcantara.github.io/",
                        "montival_junior@yahoo.com.br"),
                "Apache License Version 2.0",
                "https://www.apache.org/licesen.html", new ArrayList<>()
        );
    }

}

