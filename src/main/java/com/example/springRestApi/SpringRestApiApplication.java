package com.example.springRestApi;

/**
 * add swagger(next steps)
 * import io.swagger.v3.oas.annotations.OpenAPIDefinition;
 * import io.swagger.v3.oas.annotations.info.Info;
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
@EnableJpaAuditing
@SpringBootApplication
public class SpringRestApiApplication  {

    public static void main(String[] args) {
        SpringApplication.run(SpringRestApiApplication .class, args);
    }

}
