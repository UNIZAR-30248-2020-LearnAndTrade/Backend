package com.project.LearnAndTrade.Swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Server;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public Docket buildDocket() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.project.LearnAndTrade.Controller"))
                .paths(PathSelectors.any())
                .build()
                .servers(new Server("Public API", "https://learn-and-trade-backend.herokuapp.com:443", "", new ArrayList<>(), new ArrayList<>()))
                .apiInfo(this.apiInfo());
    }

    private ApiInfo apiInfo() {
        ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
        return apiInfoBuilder
                .title("Learn & Trade API")
                .description("This API documentation contains the necessary information to fully understand what the platform can do.")
                .version("0.1.0")
                .build();
    }
}