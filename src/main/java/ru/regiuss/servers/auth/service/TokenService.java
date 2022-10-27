package ru.regiuss.servers.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.regiuss.servers.auth.dto.TokenDTO;
import ru.regiuss.servers.auth.model.RefreshToken;
import ru.regiuss.servers.auth.model.User;
import ru.regiuss.servers.auth.repository.RefreshTokenRepository;
import ru.regiuss.servers.auth.util.JwtUtils;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final JwtUtils jwtUtils;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${app.jwtTokens.accessToken.secret}")
    private String accessTokenSecret;

    @Value("${app.jwtTokens.accessToken.expiration}")
    private long accessTokenExpiration;

    @Value("${app.jwtTokens.refreshToken.secret}")
    private String refreshTokenSecret;

    @Value("${app.jwtTokens.refreshToken.expiration}")
    private long refreshTokenExpiration;

    public TokenDTO createAccessToken(User user) {
        return jwtUtils.generateToken(
                Integer.toString(user.getId()),
                new Date(System.currentTimeMillis() + accessTokenExpiration),
                accessTokenSecret.getBytes(StandardCharsets.UTF_8)
        );
    }

    public TokenDTO createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setCreateDate(Instant.now());
        refreshToken.setExpirationDate(Instant.ofEpochMilli(System.currentTimeMillis() + refreshTokenExpiration));
        refreshTokenRepository.saveAndFlush(refreshToken);
        return jwtUtils.generateToken(
                Integer.toString(refreshToken.getId()),
                new Date(refreshToken.getExpirationDate().toEpochMilli()),
                refreshTokenSecret.getBytes(StandardCharsets.UTF_8)
        );
    }

    public RefreshToken getRefreshToken(String refreshToken) {
        Integer id = jwtUtils.getSubjectFromToken(refreshToken, refreshTokenSecret.getBytes(StandardCharsets.UTF_8));
        Optional<RefreshToken> optional = refreshTokenRepository.findById(id);
        if(optional.isEmpty()) throw new RuntimeException("token not found");
        return optional.get();
    }

    public Integer getIdFromAccessToken(String accessToken) {
        return jwtUtils.getSubjectFromToken(accessToken, accessTokenSecret.getBytes(StandardCharsets.UTF_8));
    }

    public void delete(RefreshToken refreshToken) {
        refreshTokenRepository.delete(refreshToken);
    }
}
