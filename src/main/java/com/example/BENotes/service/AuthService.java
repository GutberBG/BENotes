package com.example.BENotes.service;

import com.example.BENotes.entity.User;
import com.example.BENotes.repository.UserRepository;
import com.example.BENotes.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils; // Inyectamos JwtUtils

    @Autowired
    private PasswordEncoder passwordEncoder; // Inyectamos el PasswordEncoder

    public String login(String username, String password) {
        // Buscar al usuario en la base de datos
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        // Verificar la contraseña encriptada
        if (passwordEncoder.matches(password, user.getPassword())) {
            // Generar el JWT
            return jwtUtils.generateJwtToken(user);
        }

        throw new RuntimeException("Invalid credentials");
    }
}
