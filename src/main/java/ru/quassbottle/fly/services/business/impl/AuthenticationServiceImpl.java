package ru.quassbottle.fly.services.business.impl;

import lombok.NonNull;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.quassbottle.fly.dto.request.LoginRequest;
import ru.quassbottle.fly.dto.request.RegisterRequest;
import ru.quassbottle.fly.dto.response.AccountRegisteredResponse;
import ru.quassbottle.fly.dto.response.JwtResponse;
import ru.quassbottle.fly.entities.Account;
import ru.quassbottle.fly.helpers.AccountFieldValidator;
import ru.quassbottle.fly.services.business.AuthenticationService;
import ru.quassbottle.fly.services.crud.AccountService;
import ru.quassbottle.fly.services.security.JwtProvider;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final AccountService accountService;
    private final AccountFieldValidator accountFieldValidator;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public AuthenticationServiceImpl(PasswordEncoder passwordEncoder,
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
        String firstname = request.getFirstname();
        String lastname = request.getLastname();
        String username = request.getUsername();

        if (!accountFieldValidator.isValidEmail(email)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Invalid email");
        }
        if (!accountFieldValidator.isValidPassword(password)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Invalid password (minimal length is 8)");
        }
        if (accountService.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Account already exists");
        }
        if (!accountFieldValidator.isValidUsername(username)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Invalid username");
        }
        if (accountService.existsByUsername(username)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Username already claimed");
        }
        if (!accountFieldValidator.isValidName(firstname)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Invalid first name");
        }
        if (!accountFieldValidator.isValidName(lastname)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Invalid last name");
        }
        
        String passwordEncoded = passwordEncoder.encode(password);

        Account account = Account.builder()
                .email(email)
                .hashedPassword(passwordEncoded)
                .firstname(firstname)
                .lastname(lastname)
                .username(username)
                .build();
        Account accountDb = this.accountService.save(account);

        return new AccountRegisteredResponse(accountDb.getId(), accountDb.getEmail(),
                accountDb.getCreatedAt());
    }

    @Override
    public JwtResponse login(@NonNull LoginRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        if (!accountFieldValidator.isValidEmail(email)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Invalid email");
        }
        if (!accountFieldValidator.isValidPassword(password)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Invalid password (minimal length is 8)");
        }
        
        Authentication authentication = this.authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Account candidate = this.accountService.findByEmail(email).orElseThrow(() ->
                new ResponseStatusException(HttpStatusCode.valueOf(400), "Account with such email doesn't exist"));

        String token = this.jwtProvider.generateAccessToken(authentication.getName(), authentication.getAuthorities());
        String refreshToken = this.jwtProvider.generateRefreshToken(authentication.getName());

        candidate.setRefreshToken(refreshToken);

        this.accountService.update(candidate, candidate.getId());

        return new JwtResponse(token, refreshToken);
    }


}
