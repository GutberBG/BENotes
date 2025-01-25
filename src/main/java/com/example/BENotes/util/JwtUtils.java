package com.example.BENotes.util;

import com.example.BENotes.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.SignatureException;
import java.util.Base64;
import java.util.Date;
@Component
public class JwtUtils {

    private final SecretKey jwtSecret = Keys.secretKeyFor(SignatureAlgorithm.HS512);// Define la clave secreta

    private final int jwtExpirationMs = 86400000; // 24 horas

    // Método para generar el token JWT
    public String generateJwtToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    // Método para obtener el nombre de usuario desde el JWT
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Método para validar el token JWT
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Método para extraer el JWT del encabezado
    public String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }
}