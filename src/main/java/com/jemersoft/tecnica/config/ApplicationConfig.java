package com.jemersoft.tecnica.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class ApplicationConfig {

    @Configuration
    public class CorsConfig implements WebMvcConfigurer {

        @Value("${client_host}")
        private String frontendUrl;

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedOrigins(frontendUrl)
                    .allowedMethods("*")
                    .allowedHeaders("*")
                    .maxAge(3600);
        }
    }

}
