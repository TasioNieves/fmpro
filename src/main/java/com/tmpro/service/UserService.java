package com.tmpro.service;

import com.tmpro.model.Role;
import com.tmpro.model.User;
import com.tmpro.repository.RoleRepository;
import com.tmpro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Usa el PasswordEncoder configurado

    /**
     * Registra un nuevo usuario con un rol (por su id de rol).
     *
     * @param username El nombre de usuario.
     * @param password La contraseña del usuario.
     * @param roleId El id del rol del usuario (por ejemplo, 1 para "Admin").
     * @return El usuario registrado.
     */
    public User registerUser(String username, String password, Long roleId) {
        // Verifica si el usuario ya existe
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("El nombre de usuario ya está en uso.");
        }

        // Codifica la contraseña
        String encodedPassword = passwordEncoder.encode(password);

        // Busca el rol por su id
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el rol con ID: " + roleId));

        // Crea un nuevo usuario con el rol
        User user = new User();
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setRole(role);

        // Guarda el usuario en la base de datos
        return userRepository.save(user);
    }

    /**
     * Verifica las credenciales de un usuario para el login.
     *
     * @param username El nombre de usuario.
     * @param password La contraseña del usuario.
     * @return El usuario autenticado.
     */
    public User authenticateUser(String username, String password) {
        // Busca el usuario por su nombre
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + username));

        // Verifica la contraseña
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Contraseña incorrecta.");
        }

        return user; // Devuelve el usuario autenticado
    }

    /**
     * Busca un usuario por su nombre de usuario.
     *
     * @param username El nombre de usuario.
     * @return El usuario encontrado.
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
