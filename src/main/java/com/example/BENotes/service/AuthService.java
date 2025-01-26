package com.example.BENotes.service;

import com.example.BENotes.dto.AuthResponse;
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
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthResponse login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        // Verificar la contraseña encriptada
        if (passwordEncoder.matches(password, user.getPassword())) {
            // Generar el JWT
            String token = jwtUtils.generateJwtToken(user);

            // Crear un objeto con el token y el id del usuario
            return new AuthResponse(token, user.getId());
        }

        throw new RuntimeException("Invalid credentials");
    }
}
