package com.tmpro.service;

import com.tmpro.model.Role;
import com.tmpro.model.User;
import com.tmpro.repository.RoleRepository;
import com.tmpro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Registra un nuevo usuario con el rol especificado por nombre.
     *
     * @param username Nombre de usuario.
     * @param password Contraseña.
     * @return Usuario registrado.
     */
    public User register(String username, String password, Long roleId) {
        // Verificar si el usuario ya existe
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("El nombre de usuario ya está en uso.");
        }

        // Buscar el rol por nombre
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el rol: " + roleId));

        // Codificar la contraseña
        String encodedPassword = passwordEncoder.encode(password);

        // Crear el usuario con el rol encontrado
        User user = new User();
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setRole(role.getId());

        // Guardar el usuario en la base de datos
        return userRepository.save(user);
    }

    /**
     * Inicia sesión con el usuario.
     *
     * @param username Nombre de usuario.
     * @param password Contraseña.
     * @return Usuario autenticado.
     */
    public User login(String username, String password) {
        // Buscar el usuario
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + username));

        // Verificar la contraseña
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Contraseña incorrecta.");
        }

        return user;
    }
}
