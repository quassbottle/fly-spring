package ru.quassbottle.fly.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class JwtResponse {
    private final String type = "Bearer";

    private String accessToken;
    private String refreshToken;
}
