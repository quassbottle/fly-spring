package ru.quassbottle.fly.services.business;

import ru.quassbottle.fly.dto.request.RefreshTokenRequest;
import ru.quassbottle.fly.dto.response.JwtResponse;
import ru.quassbottle.fly.dto.response.RefreshTokenResponse;

public interface RefreshTokenService {
    RefreshTokenResponse refresh(RefreshTokenRequest request);
}
