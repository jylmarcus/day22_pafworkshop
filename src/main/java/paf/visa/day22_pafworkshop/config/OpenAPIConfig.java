package paf.visa.day22_pafworkshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenAPIConfig {
    
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
        .info(new Info()
        .title("PAF Day 22 Workshop on Swagger")
        .description("PAF Day 22 Workshop")
        .version("version 1.0"));
    }
}
