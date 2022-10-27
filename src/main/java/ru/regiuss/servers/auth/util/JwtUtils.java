package ru.regiuss.servers.auth.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.regiuss.servers.auth.dto.TokenDTO;

import java.util.Date;

@Component
@Slf4j
public class JwtUtils {
    public TokenDTO generateToken(String subject, Date expiration, byte[] secret) {
        String token = Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(expiration)
                .signWith(Keys.hmacShaKeyFor(secret))
                .compact();
        TokenDTO dto = new TokenDTO();
        dto.setToken(token);
        dto.setExpiration(expiration.getTime());
        return dto;
    }

    public Integer getSubjectFromToken(String token, byte[] secret) {
        try {
            return Integer.parseInt(Jwts.parserBuilder().setSigningKey(secret).
                    build().parseClaimsJws(token).getBody().getSubject());
        } catch (Exception e) {
            log.error("Invalid JWT access token: {}", e.getMessage());
        }
        return -1;
    }
}
