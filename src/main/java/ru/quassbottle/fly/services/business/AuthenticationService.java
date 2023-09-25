package ru.quassbottle.fly.services.business;

import lombok.NonNull;
import ru.quassbottle.fly.dto.request.LoginRequest;
import ru.quassbottle.fly.dto.request.RegisterRequest;
import ru.quassbottle.fly.dto.response.AccountRegisteredResponse;
import ru.quassbottle.fly.dto.response.JwtResponse;

public interface AuthorizationService {
    AccountRegisteredResponse register(RegisterRequest request);
    JwtResponse login(LoginRequest request);
}
