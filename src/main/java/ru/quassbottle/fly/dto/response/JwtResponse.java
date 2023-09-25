package ru.quassbottle.fly.dto.response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JwtResponse {
    private final String type = "Bearer";

    private String accessToken;
    private String refreshToken;
}
