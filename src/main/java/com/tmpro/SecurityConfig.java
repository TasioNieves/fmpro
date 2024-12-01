package com.tmpro;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desactiva CSRF si no usas tokens
                .authorizeHttpRequests(auth -> auth

                        .anyRequest().authenticated() // Requiere autenticación para otras rutas
                )
                .formLogin(form -> form.disable()); // Desactiva el formulario de login predeterminado

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
