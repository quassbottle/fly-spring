package ru.quassbottle.fly.services.business.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.quassbottle.fly.dto.request.RefreshTokenRequest;
import ru.quassbottle.fly.dto.response.RefreshTokenResponse;
import ru.quassbottle.fly.entities.Account;
import ru.quassbottle.fly.services.business.RefreshTokenService;
import ru.quassbottle.fly.services.crud.AccountService;
import ru.quassbottle.fly.services.security.JwtProvider;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final JwtProvider jwtProvider;
    private final AccountService accountService;

    @Autowired
    public RefreshTokenServiceImpl(JwtProvider jwtProvider,
                                   AccountService accountService) {
        this.jwtProvider = jwtProvider;
        this.accountService = accountService;
    }

    @Override
    public RefreshTokenResponse refresh(RefreshTokenRequest request) {
        if (!jwtProvider.validateRefreshToken(request.getRefreshToken())) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(403), "Invalid refresh token");
        }

        var claims = jwtProvider.getRefreshClaims(request.getRefreshToken());
        String email = claims.getSubject();

        Account account = this.accountService.findByEmail(email).orElseThrow(() ->
                new ResponseStatusException(HttpStatusCode.valueOf(403), "User with such email not found"));

        String token = jwtProvider.generateAccessToken(account.getEmail(), account.getRoles());

        return new RefreshTokenResponse(token);
    }
}
