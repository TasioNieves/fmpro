package com.tmpro.controller;

import com.tmpro.model.UserDto;
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

    /**
     * Endpoint para registrar un nuevo usuario.
     *
     * @param userDto Contiene el nombre de usuario, la contraseña y el rol.
     * @return El usuario registrado.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) {
        try {
            User newUser = authService.register(userDto.getUsername(), userDto.getPassword(), userDto.getRole());
            return ResponseEntity.ok(newUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Endpoint para autenticar a un usuario.
     *
     * @param user Contiene el nombre de usuario y la contraseña.
     * @return El usuario autenticado.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            User authenticatedUser = authService.login(user.getUsername(), user.getPassword());
            return ResponseEntity.ok(authenticatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
