package education.technicalcareer.Notes.API.util;

import education.technicalcareer.Notes.API.user_management.repositories.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {

    @Value("${app.jwtSecret}") // Get the secret from application.properties
    private String jwtSecret;

    @Value("${app.jwtExpirationMs}") // Get expiration time from properties
    private int jwtExpirationMs;
    private static final Key SECRET_KEY = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS512); // Secure key
    public String generateToken(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);
        System.out.println("35 " + userPrincipal.getUsername() );
        Map<String, Object> claims = new HashMap<>();
        //claims.put("id", userPrincipal.get()); // Or userPrincipal.getId() if you have an ID
        claims.put("username", userPrincipal.getUsername());
        // Add other claims as needed (e.g., roles)

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
               // .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
       // claims.get
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            //Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            // Log the exception for debugging
            return false;
        }
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
