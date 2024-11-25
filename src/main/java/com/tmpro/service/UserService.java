package com.tmpro.service;

import com.tmpro.model.Role;
import com.tmpro.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

@Service
public class UserService {

    // Clave secreta para firmar el token JWT (debe ser segura y secreta)
    private static final String SECRET_KEY = "mi-clave-secreta";

    public User registerUser(String username, String password, Role role) {
        // Lógica para registrar al usuario
        // Crear un nuevo usuario y guardarlo en la base de datos
        return new User(username, password, role);
    }

    /**
     * Genera un token JWT para el usuario autenticado.
     * @param authentication Información de autenticación del usuario.
     * @return El token JWT.
     */
    // Obtén el secreto de las propiedades de la aplicación
    @Value("${jwt.secret}")
    private String secretKey;

    public String generateJwtToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // Expira en 24 horas
                .signWith(SignatureAlgorithm.HS256, secretKey)  // Usa el secretKey desde properties
                .compact();
    }
}
