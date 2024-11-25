package com.tmpro;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .requestMatchers("/login", "/register").permitAll()  // Rutas públicas
                .anyRequest().authenticated()  // Resto de rutas requieren autenticación
                .and()
                .formLogin(login -> login
                        .loginPage("/login")  // Página de login personalizada
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")  // URL para hacer logout
                        .permitAll());

        return http.build();
    }
}
