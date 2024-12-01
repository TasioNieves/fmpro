package com.tmpro.controller;

import com.tmpro.model.Role;
import com.tmpro.model.UserDto;
import com.tmpro.model.User;
import com.tmpro.repository.RoleRepository;
import com.tmpro.repository.UserRepository;
import com.tmpro.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
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
    /**
     * Endpoint para registrar un nuevo usuario.
     *
     * @param userDto Contiene el nombre de usuario, la contraseña y el rol.
     * @return El usuario registrado.
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User userDTO) {
        // Obtener el rol por nombre
        Optional<Role> role = roleRepository.findByName(userDTO.getRole());
        if (role == null) {
            return ResponseEntity.badRequest().body("El rol no existe");
        }

        // Crear el nuevo usuario con el rol encontrado
        User user = new User(userDTO.getUsername(), userDTO.getPassword(), role);
        userRepository.save(user);

        return ResponseEntity.ok("Usuario registrado con éxito");
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
