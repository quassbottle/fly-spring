package ru.quassbottle.fly.controllers.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.quassbottle.fly.dto.request.LoginRequest;
import ru.quassbottle.fly.dto.request.RegisterRequest;
import ru.quassbottle.fly.entities.Account;
import ru.quassbottle.fly.services.business.JwtProvider;
import ru.quassbottle.fly.services.crud.AccountService;
import ru.quassbottle.fly.services.crud.ProfileService;

@RestController
@RequestMapping("/api/")
public class AuthController {
    private final AccountService accountService;
    private final ProfileService profileService;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(AccountService accountService,
                          ProfileService profileService,
                          JwtProvider jwtProvider,
                          PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager) {
        this.accountService = accountService;
        this.profileService = profileService;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping
    public String tt() {
        return "tt";
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) { // haram, decompose
        String email = request.getEmail();
        String password = request.getPassword();

        String passwordEncoded = passwordEncoder.encode(password);

        Account account = Account.builder().email(email).hashedPassword(passwordEncoded).build();
        Account accountDb = this.accountService.save(account);

        return ResponseEntity.ok(accountDb);
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Account candidate = this.accountService.findByEmail(email).get();

        String token = jwtProvider.generateAccessToken(authentication);

        return ResponseEntity.ok(token);
    }
}
