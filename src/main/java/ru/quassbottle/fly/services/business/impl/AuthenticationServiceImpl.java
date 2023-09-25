package ru.quassbottle.fly.services.business.impl;

import lombok.NonNull;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import ru.quassbottle.fly.dto.request.LoginRequest;
import ru.quassbottle.fly.dto.request.RegisterRequest;
import ru.quassbottle.fly.dto.response.AccountRegisteredResponse;
import ru.quassbottle.fly.dto.response.JwtResponse;
import ru.quassbottle.fly.entities.Account;
import ru.quassbottle.fly.helpers.AccountFieldValidator;
import ru.quassbottle.fly.services.business.AuthorizationService;
import ru.quassbottle.fly.services.crud.AccountService;
import ru.quassbottle.fly.services.security.JwtProvider;

public class AuthorizationServiceImpl implements AuthorizationService {
    private final PasswordEncoder passwordEncoder;
    private final AccountService accountService;
    private final AccountFieldValidator accountFieldValidator;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public AuthorizationServiceImpl(PasswordEncoder passwordEncoder,
                                    AccountService accountService,
                                    AccountFieldValidator accountFieldValidator,
                                    AuthenticationManager authenticationManager,
                                    JwtProvider jwtProvider) {
        this.passwordEncoder = passwordEncoder;
        this.accountService = accountService;
        this.accountFieldValidator = accountFieldValidator;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public AccountRegisteredResponse register(@NonNull RegisterRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        if (!accountFieldValidator.isValidEmail(email)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(403), "Invalid email");
        }
        if (!accountFieldValidator.isValidPassword(password)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(403), "Invalid password (minimal length is 8)");
        }
        
        String passwordEncoded = passwordEncoder.encode(password);

        Account account = Account.builder().email(email).hashedPassword(passwordEncoded).build();
        Account accountDb = this.accountService.save(account);

        return new AccountRegisteredResponse(accountDb.getId(), accountDb.getEmail(),
                accountDb.getUsername(), accountDb.getCreatedAt());
    }

    @Override
    public JwtResponse login(@NonNull LoginRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        if (!accountFieldValidator.isValidEmail(email)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(403), "Invalid email");
        }
        if (!accountFieldValidator.isValidPassword(password)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(403), "Invalid password (minimal length is 8)");
        }
        
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Account candidate = this.accountService.findByEmail(email).get();

        String token = jwtProvider.generateAccessToken(authentication);

    }
}
