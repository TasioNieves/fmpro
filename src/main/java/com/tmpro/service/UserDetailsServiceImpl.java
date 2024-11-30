package com.tmpro.service;

import com.tmpro.model.User;
import com.tmpro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepository; // Repositorio de usuarios

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Buscar el usuario en la base de datos
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Convertir el usuario a UserDetails (usando la clase User de Spring Security)
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                // Convertir los roles a GrantedAuthority
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName())) // Asumiendo que Role tiene un método getName()
                        .collect(Collectors.toList())
        );
    }
}
