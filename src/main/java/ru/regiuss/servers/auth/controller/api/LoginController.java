package ru.regiuss.servers.auth.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.regiuss.servers.auth.dto.JwtResponse;
import ru.regiuss.servers.auth.dto.RefreshTokenDTO;
import ru.regiuss.servers.auth.model.LoginRequest;
import ru.regiuss.servers.auth.model.RefreshToken;
import ru.regiuss.servers.auth.model.User;
import ru.regiuss.servers.auth.model.UserDetailsImpl;
import ru.regiuss.servers.auth.repository.UserRepository;
import ru.regiuss.servers.auth.service.TokenService;

import java.time.Instant;

@RestController
@RequiredArgsConstructor
public class LoginController {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;


    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@ModelAttribute LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setAccessToken(tokenService.createAccessToken(principal.getUser()));
        jwtResponse.setRefreshToken(tokenService.createRefreshToken(principal.getUser()));
        jwtResponse.setServerTime(System.currentTimeMillis());
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshTokenDTO dto) {
        RefreshToken refreshToken = tokenService.getRefreshToken(dto.getRefreshToken());
        User user = refreshToken.getUser();
        tokenService.delete(refreshToken);
        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setAccessToken(tokenService.createAccessToken(user));
        jwtResponse.setRefreshToken(tokenService.createRefreshToken(user));
        jwtResponse.setServerTime(System.currentTimeMillis());
        return ResponseEntity.ok(jwtResponse);
    }
}
