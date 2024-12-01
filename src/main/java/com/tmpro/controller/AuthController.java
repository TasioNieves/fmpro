package com.tmpro.controller;

import com.tmpro.model.User;
import com.tmpro.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User userDto) {
        try {
            // Intentar registrar el usuario
            User newUser = authService.register(userDto.getUsername(), userDto.getPassword(), userDto.getRole());
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            // Ya no autenticamos con JWT, solo devolvemos el usuario autenticado
            User authenticatedUser = authService.login(user.getUsername(), user.getPassword());
            return ResponseEntity.ok(authenticatedUser); // Solo retornamos el usuario
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
