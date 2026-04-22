package com.bot.loginAndRegisterationApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig implements WebMvcConfigurer{
 
@Bean 
public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Interview Scheduler API End points").version("v1")
                        .description("Interview Scheduler API End points")
                        .contact(new Contact()
                                .name("Satish VJ")
                                .email("")));  
        }

} 
