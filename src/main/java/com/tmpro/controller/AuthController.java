package com.tmpro.controller;

import com.tmpro.model.Role;

import com.tmpro.model.User;
import com.tmpro.model.UserRequest;
import com.tmpro.model.UserResponse;
import com.tmpro.repository.RoleRepository;
import com.tmpro.repository.UserRepository;
import com.tmpro.service.AuthService;
import com.tmpro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest userRequest) {

        UserResponse userResponse = userService.registerUser(userRequest);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
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
