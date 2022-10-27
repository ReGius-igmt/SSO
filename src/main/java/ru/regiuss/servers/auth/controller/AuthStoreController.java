package ru.regiuss.servers.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
public class AuthStoreController {

    @GetMapping("/auth/store")
    public String authStore(HttpServletResponse response) {
        response.setHeader("Content-Security-Policy", "frame-ancestors 'self' *.trusted.com");
        return "authStore";
    }
}
