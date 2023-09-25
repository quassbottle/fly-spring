package ru.quassbottle.fly.controllers.business.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.quassbottle.fly.dto.request.LoginRequest;
import ru.quassbottle.fly.dto.request.RefreshTokenRequest;
import ru.quassbottle.fly.dto.request.RegisterRequest;
import ru.quassbottle.fly.dto.response.AccountRegisteredResponse;
import ru.quassbottle.fly.dto.response.JwtResponse;
import ru.quassbottle.fly.dto.response.RefreshTokenResponse;
import ru.quassbottle.fly.entities.Account;
import ru.quassbottle.fly.services.business.AuthenticationService;
import ru.quassbottle.fly.services.business.RefreshTokenService;
import ru.quassbottle.fly.services.business.impl.AuthenticationServiceImpl;
import ru.quassbottle.fly.services.business.impl.RefreshTokenServiceImpl;
import ru.quassbottle.fly.services.security.JwtProvider;
import ru.quassbottle.fly.services.crud.AccountService;
import ru.quassbottle.fly.services.crud.ProfileService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationService authenticationService;
    private final RefreshTokenService refreshTokenService;

    @Autowired
    public AuthController(AuthenticationService authenticationService,
                          RefreshTokenService refreshTokenService) {
        this.authenticationService = authenticationService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(this.authenticationService.login(request));
    }

    @PostMapping("register")
    public ResponseEntity<AccountRegisteredResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(this.authenticationService.register(request));
    }

    @PostMapping("refresh")
    public ResponseEntity<RefreshTokenResponse> refresh(@RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(this.refreshTokenService.refresh(request));
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleException(ResponseStatusException e) {
        return ResponseEntity.status(e.getStatusCode()).body(e.getBody());
    }
}
