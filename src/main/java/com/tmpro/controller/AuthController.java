package com.tmpro.controller;

import com.tmpro.model.Role;
import com.tmpro.model.User;
import com.tmpro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Endpoint para registrar un nuevo usuario.
     * @param request Objeto con los datos del usuario: username, password y role.
     * @return El usuario registrado.
     */
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody RegisterRequest request) {
        User user = userService.registerUser(request.getUsername(), request.getPassword(), request.getRole());
        return ResponseEntity.ok(user);
    }

    // Endpoint para iniciar sesión (login)
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest request) {
        // Autenticación del usuario con el AuthenticationManager
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // Si la autenticación es exitosa, configurar el contexto de seguridad
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generar el token JWT
        String token = userService.generateJwtToken(request.getUsername());  // Método para generar el token JWT

        // Devolver el token JWT como un 'Bearer token' en la respuesta
        return ResponseEntity.ok("Bearer " + token);
    }

    /**
     * Clase interna para representar los datos del registro.
     */
    public static class RegisterRequest {
        private String username;
        private String password;
        private Role role;  // Cambiado de String a Role

        // Constructor por defecto
        public RegisterRequest() {
        }

        // Constructor con parámetros
        public RegisterRequest(String username, String password, Role role) {
            this.username = username;
            this.password = password;
            this.role = role;
        }

        // Getters y Setters
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Role getRole() {
            return role;
        }

        public void setRole(Role role) {
            this.role = role;
        }
    }

    // Clase interna para representar los datos del login
    public static class LoginRequest {
        private String username;
        private String password;

        // Constructor por defecto
        public LoginRequest() {
        }

        // Constructor con parámetros
        public LoginRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }

        // Getters y Setters
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
