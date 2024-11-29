package com.tmpro;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Configuración explícita para deshabilitar CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // Permitir acceso público a rutas específicas
                        .requestMatchers("/", "/index.html", "/static/**", "/favicon.ico").permitAll() // Permitir acceso a recursos estáticos y página principal
                        .anyRequest().authenticated() // Requerir autenticación para otras rutas
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Usamos BCrypt para codificar contraseñas
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        // Declara el AuthenticationManager como un bean
        return authenticationConfiguration.getAuthenticationManager();
    }
}
