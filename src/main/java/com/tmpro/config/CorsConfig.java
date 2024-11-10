package com.tmpro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Permitir solicitudes CORS a todas las rutas
        registry.addMapping("/**") // Permite todas las rutas
                .allowedOrigins("http://localhost:4200", "https://tu-dominio-heroku.herokuapp.com") // URL de tu frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
                .allowedHeaders("*") // Permite todos los encabezados
                .allowCredentials(true); // Permite credenciales si es necesario
    }
}
