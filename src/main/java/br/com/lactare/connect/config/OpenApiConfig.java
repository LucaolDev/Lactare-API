package br.com.lactare.connect.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI lactareOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("Lactare Connect API")
                .version("v1")
                .description("API REST para conectar nutrizes, bancos de leite humano e gestores.")
                .contact(new Contact().name("Equipe Lactare Connect")));
    }
}
