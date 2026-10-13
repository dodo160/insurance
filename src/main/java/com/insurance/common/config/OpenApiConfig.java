package com.insurance.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI insuranceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Insurance API")
                        .description("REST API for insurance management")
                        .version("1.0.0"));
    }
}
