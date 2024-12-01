package com.tmpro.controller;

import com.tmpro.model.User;
import com.tmpro.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User registeredUser = authService.register(user.getUsername(), user.getPassword(), user.getRole());
            return ResponseEntity.ok(registeredUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
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
