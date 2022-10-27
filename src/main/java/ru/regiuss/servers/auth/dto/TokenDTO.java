package ru.regiuss.servers.auth.dto;

import lombok.Data;

@Data
public class TokenDTO {
    private String token;
    private long expiration;
}
