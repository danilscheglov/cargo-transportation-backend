package com.danilscheglov.transportation.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfiguration {

    @Value("${transportation.openapi.dev-url}")
    private String devUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL в среде разработки");

        Contact contact = new Contact();
        contact.setName("Danil Scheglov");
        contact.setUrl("https://github.com/DanilScheglov");

        Info info = new Info()
                .title("Cargo Transportation API")
                .version("1.0")
                .contact(contact)
                .description("API для управления системой транспортировки грузов");

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer))
                .tags(List.of(
                        new Tag().name("Drivers").description("API для управления водителями"),
                        new Tag().name("Cars").description("API для управления автомобилями"),
                        new Tag().name("Clients").description("API для управления клиентами"),
                        new Tag().name("Dispatchers").description("API для управления диспетчерами"),
                        new Tag().name("Mechanics").description("API для управления механиками"),
                        new Tag().name("Operators").description("API для управления операторами"),
                        new Tag().name("Flights").description("API для управления рейсами"),
                        new Tag().name("Orders").description("API для управления заказами"),
                        new Tag().name("Cargos").description("API для управления грузами"),
                        new Tag().name("Maintenance Requests").description("API для управления заявками на техническое обслуживание")));
    }
}