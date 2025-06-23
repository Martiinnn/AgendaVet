package com.AgendaVet.AgendaVet.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("AgendaVet API")
                        .version("2.0")
                        .description("API REST para el sistema de gestión de citas veterinarias AgendaVet. " +
                                   "Esta API permite gestionar usuarios, mascotas, veterinarias, reservas, reseñas y notificaciones.")
                        .contact(new Contact()
                                .name("Equipo AgendaVet")
                                .email("contacto@agendavet.com")
                                .url("https://agendavet.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Servidor de Desarrollo"),
                        new Server().url("https://api.agendavet.com").description("Servidor de Producción")
                ));
    }
}