package com.nerd.favorite18.core.api._common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class SwaggerConfig {

    @Bean
    @Profile({"local", "local-dev"})
    public OpenAPI customOpenAPILocal() {
        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("Local");

        return new OpenAPI()
                .info(new Info()
                        .title("Favorite 18 API")
                        .version("1.0")
                        .description("This is API Document of Favorite 18"))
            .servers(List.of(server));
    }

    @Bean
    @Profile({"dev", "staging", "live", "prod"})
    public OpenAPI customOpenAPIExcludeLocal() {
        Server server = new Server();
        server.setUrl("https://favorite18.kr");
        server.setDescription("DevServer");

        return new OpenAPI()
            .info(new Info()
                .title("Favorite 18 API")
                .version("1.0")
                .description("This is API Document of Favorite 18"))
            .servers(List.of(server));
    }
}