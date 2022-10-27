package ru.regiuss.servers.auth.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.regiuss.servers.auth.model.UserDetailsImpl;

@RestController
public class AccountController {

    @GetMapping("/api/v1/me")
    public ResponseEntity<?> user(Authentication authentication) {
        System.out.println(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return ResponseEntity.ok(userDetails.getUser());
    }

}
