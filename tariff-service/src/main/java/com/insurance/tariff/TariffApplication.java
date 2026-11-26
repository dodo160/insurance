package com.insurance.tariff;

import com.insurance.common.config.OpenApiConfig;
import com.insurance.common.exception.GlobalExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({OpenApiConfig.class, GlobalExceptionHandler.class})
public class TariffApplication {

    public static void main(String[] args) {
        SpringApplication.run(TariffApplication.class, args);
    }
}
