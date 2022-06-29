package com.bksoftwarevn.auction.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Lists;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .servers(Lists.newArrayList(
                        new Server().url("http://localhost:8080"),
                        new Server().url("https://auctions.bksoftwarevn.com")
                ))
                .info(new Info().title("Auctions Application API")
                        .description("OpenAPI 3.0")
                        .contact(new Contact()
                                .email("manhnd@bksoftwarevn.com")
                                .name("manhnd")
                                .url("https://bksoftwarevn.com/"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html"))
                        .version("1.0.0"));
    }
}