package com.example.springRestApi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Classe de configuração para o Swagger.
 * O Swagger é usado para documentação da API.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    /**
     * Configura o Swagger para documentar todas as APIs no pacote base especificado.
     *
     * @return Um objeto Docket, que o Swagger usa para gerar documentação da API.
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.springRestApi.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}

