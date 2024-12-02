package com.tmpro.service;

import com.tmpro.model.Role;
import com.tmpro.model.User;
import com.tmpro.model.UserRequest;
import com.tmpro.model.UserResponse;
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


    public UserResponse registerUser(UserRequest userRequest) {
        // Busca el rol en la base de datos
        Role role = roleRepository.findById(userRequest.getRole())
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado: " + userRequest.getRole()));

        // Crea el usuario
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRole(role.getId());

        // Guarda el usuario en la base de datos
        userRepository.save(user);

        // Retorna una respuesta personalizada
        return new UserResponse(user.getId(), user.getUsername(), user.getRole());
    }

    /**
     * Obtiene el rol por defecto.
     *
     * @return Un rol por defecto (por ejemplo, "User").
     */
    private Role getDefaultRole() {
        return roleRepository.findById(10L) // Ajusta el nombre del rol por defecto según tu base de datos
                .orElseThrow(() -> new IllegalStateException("El rol por defecto no está configurado en la base de datos."));
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
