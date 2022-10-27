package ru.regiuss.servers.auth.model;

import lombok.Data;

@Data
public class LoginRequest {
    private String login;
    private String password;
}
