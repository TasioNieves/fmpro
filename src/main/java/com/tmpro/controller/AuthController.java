package com.tmpro.controller;

import com.tmpro.model.User;
import com.tmpro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

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

    /**
     * Clase interna para representar los datos del registro.
     */
    public static class RegisterRequest {
        private String username;
        private String password;
        private String role;

        // Constructor por defecto
        public RegisterRequest() {
        }

        // Constructor con parámetros
        public RegisterRequest(String username, String password, String role) {
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

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }
}
