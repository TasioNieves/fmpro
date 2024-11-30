package com.tmpro.service;

import com.tmpro.model.User;
import com.tmpro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Registra un nuevo usuario.
     * @param username Nombre de usuario.
     * @param password Contraseña del usuario.
     * @param role Rol del usuario.
     * @return Usuario registrado.
     * @throws RuntimeException Si el usuario ya existe.
     */
    public User register(String username, String password, String role) {
        // Verificar si el usuario ya existe
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("El usuario ya existe.");
        }

        // Crear y guardar el nuevo usuario
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);

        return userRepository.save(user);
    }

    /**
     * Autentica un usuario con su nombre de usuario y contraseña.
     * @param username Nombre de usuario.
     * @param password Contraseña.
     * @return Usuario autenticado.
     * @throws RuntimeException Si las credenciales son inválidas.
     */
    public User login(String username, String password) {
        // Buscar el usuario en la base de datos
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado.");
        }

        // Verificar la contraseña
        User user = optionalUser.get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta.");
        }

        return user;
    }
}
