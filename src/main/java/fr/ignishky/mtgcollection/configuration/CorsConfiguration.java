package fr.ignishky.mtgcollection.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration {

    private static final String FRONT_DOMAIN = "http://localhost:8080";

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/blocks/**").allowedMethods("GET").allowedOrigins(FRONT_DOMAIN);
                registry.addMapping("/sets/**").allowedMethods("GET").allowedOrigins(FRONT_DOMAIN);
                registry.addMapping("/collection/**").allowedMethods("PUT").allowedOrigins(FRONT_DOMAIN);
            }
        };
    }

}
