package ru.regiuss.servers.auth.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ru.regiuss.servers.auth.model.User;
import ru.regiuss.servers.auth.model.UserDetailsImpl;
import ru.regiuss.servers.auth.util.JwtUtils;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println(authentication);
        System.out.println(request.getRequestURI());
        User user = new User();
        user.setId(1);
        user.setUsername("root");
        user.setPassword("111");
        user.setGithub("23422");
        user.setGoogle("43434");
        user.setTg("11111");
        Cookie cookie = new Cookie("AUTH_TOKEN", jwtUtils.generateToken("sa", new Date(), new byte[]{}).getToken());
        cookie.setPath("/");
        cookie.setVersion(1);
        response.addCookie(cookie);
        response.sendRedirect("/");
    }
}
