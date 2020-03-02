package com.base.movingwalls.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableJpaRepositories(basePackages = "com.base.movingwalls.repository")
@EnableTransactionManagement
@EntityScan(basePackages = "com.base.movingwalls.model")
@EnableSwagger2
public class MovingWallsConfiguration {

    @Bean
    public Docket MovingWallsAPI() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("Moving Walls Campaign API")
                        .description("Provides APIs for the Login and Campaign Page.")
                        .version("0.1.0")
                        .build())
                .select()
                .paths(PathSelectors.any())
                .build();
    }
}
