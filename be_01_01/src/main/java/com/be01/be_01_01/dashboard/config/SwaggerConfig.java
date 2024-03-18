package com.be01.be_01_01.dashboard.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(){
        Info info = new Info()
                .title("1차 프로젝트")
                .description("~~~")
                .contact(new Contact().name("슈퍼코딩"));
        return new OpenAPI()
                .info(info);
    }
}
