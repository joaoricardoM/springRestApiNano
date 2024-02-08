package com.example.springRestApi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
@EnableJpaAuditing
@OpenAPIDefinition(info = @Info(title = "Extractor XML API", version = "1.0", description = "API for Extractor XML"))
@SpringBootApplication
public class SpringRestApiApplication  {

    public static void main(String[] args) {
        SpringApplication.run(SpringRestApiApplication .class, args);
    }

}
