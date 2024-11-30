package com.tmpro.service;

import com.tmpro.model.Role;
import com.tmpro.model.User;
import com.tmpro.repository.RoleRepository;
import com.tmpro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

     private final JwtConfig jwtConfig;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder  bCryptpasswordEncoder;

    public UserService(JwtConfig jwtConfig, PasswordEncoder passwordEncoder) {
        this.jwtConfig = jwtConfig;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registra un nuevo usuario con un rol (como nombre de rol).
     * @param username El nombre de usuario.
     * @param password La contraseña del usuario.
     * @param roleName El nombre del rol del usuario (por ejemplo "ROLE_USER").
     * @return El usuario registrado.
     */
    public User registerUser(String username, String password, String roleName) {
        // Codificar la contraseña
        String encodedPassword = passwordEncoder.encode(password);

        // Buscar el rol por nombre
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new IllegalArgumentException("Role not found: " + roleName));

        // Crear un Set con el rol
        Set<Role> roles = Collections.singleton(role);

        // Crear un nuevo usuario con el rol
        User user = new User(username, encodedPassword, roles);

        // Guardar el usuario en la base de datos
        return userRepository.save(user);
    }

    /**
     * Genera un token JWT para el usuario autenticado.
     *
     * @param username El nombre de usuario.
     * @return El token JWT.
     */
    public String generateJwtToken(String username) {
        String secretKey = jwtConfig.getSecretKey();
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24 horas de validez
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

    }

    /**
     * Obtiene un usuario por su nombre de usuario.
     *
     * @param username El nombre de usuario.
     * @return El usuario encontrado.
     */
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));
    }
}
