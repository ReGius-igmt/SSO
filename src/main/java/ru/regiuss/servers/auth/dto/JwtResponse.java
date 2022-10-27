package ru.regiuss.servers.auth.dto;

import lombok.Data;

@Data
public class JwtResponse {
    private TokenDTO accessToken;
    private TokenDTO refreshToken;
    private long serverTime;
}
