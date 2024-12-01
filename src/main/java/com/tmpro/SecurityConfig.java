package com.tmpro;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Desactiva CSRF si no usas tokens

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/","/api/roles/", "/index.html", "/api/**", "/static/**", "/styles.css", "/runtime.js", "/polyfills.js", "/vendor.js", "/main.js", "/favicon.ico").permitAll() // Permite acceso público a estas rutas


                )
                .formLogin(AbstractHttpConfigurer::disable); // Desactiva el formulario de login predeterminado


        return http.build();
    }



    /**
     * Proporciona un `PasswordEncoder` para codificar y verificar contraseñas.
     * Aunque no usamos Spring Security, este Bean es útil para gestionar contraseñas de manera segura.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
