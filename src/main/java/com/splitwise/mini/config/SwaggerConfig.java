package com.splitwise.mini.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/**")
                .build();
    }
    
    @Bean
    public OpenAPI billSplitterOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Bill Splitter")
                        .description("API for managing teams, expenses, and settlements in a bill-splitting system.")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Amit")
                                .url("https://github.com/amit8683/Bill_Splitter")
                                .email("amit00868@gmail.com"))
                        .license(new License()
                                .name("")
                                ));
    }
}

