package ru.quassbottle.fly.services.business.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.quassbottle.fly.dto.request.PersonalInfoUpdateDto;
import ru.quassbottle.fly.dto.response.ProfileCreatedResponse;
import ru.quassbottle.fly.entities.Account;
import ru.quassbottle.fly.entities.Profile;
import ru.quassbottle.fly.helpers.AccountFieldValidator;
import ru.quassbottle.fly.services.business.AccountManagementService;
import ru.quassbottle.fly.services.crud.AccountService;

import java.util.Optional;

@Service
public class AccountManagementServiceImpl implements AccountManagementService {
    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;
    private final AccountFieldValidator accountFieldValidator;

    @Autowired
    public AccountManagementServiceImpl(AccountService accountService,
                                        PasswordEncoder passwordEncoder,
                                        AccountFieldValidator accountFieldValidator) {
        this.accountService = accountService;
        this.passwordEncoder = passwordEncoder;
        this.accountFieldValidator = accountFieldValidator;
    }

    @Override
    public boolean changePassword(String email, String password) {
        Account candidate = this.accountService.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(403), "Account with such email not found"));

        if (password == null || password.isBlank() || !accountFieldValidator.isValidPassword(password)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(403), "Invalid password");
        }
        candidate.setHashedPassword(this.passwordEncoder.encode(password));

        return this.accountService.update(candidate, candidate.getId()) != null;
    }

    @Override
    public boolean changeEmail(String oldEmail, String newEmail) {
        Account candidate = this.accountService.findByEmail(oldEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Account with such email not found"));

        if (newEmail == null || newEmail.isBlank() || !accountFieldValidator.isValidEmail(newEmail)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(403), "Invalid password");
        }
        candidate.setEmail(newEmail);

        return this.accountService.update(candidate, candidate.getId()) != null;
    }

    @Override
    public boolean updatePersonalInfo(String email, PersonalInfoUpdateDto personalInfoUpdateDto) {
        Account candidate = this.accountService.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Account with such email not found"));

        String firstname = personalInfoUpdateDto.getFirstname();
        String lastname = personalInfoUpdateDto.getLastname();
        String username = personalInfoUpdateDto.getUsername();

        if (!accountFieldValidator.isValidName(firstname)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(403), "Invalid firstname");
        }
        candidate.setFirstname(firstname);

        if (!accountFieldValidator.isValidName(lastname)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(403), "Invalid lastname");
        }
        candidate.setLastname(lastname);

        if (!accountFieldValidator.isValidUsername(username)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(403), "Invalid username");
        }
        if (accountService.existsByUsername(username)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(403), "User with such username already exists");
        }
        candidate.setUsername(username);

        return this.accountService.update(candidate, candidate.getId()) != null;
    }

    @Override
    public boolean deleteProfile(String email, Long profileId) {
        return false;
    }

    @Override
    public ProfileCreatedResponse createProfile(String email, boolean isWorker) {
        Account candidate = this.accountService.findByEmail(email).orElseThrow(() ->
                new ResponseStatusException(HttpStatusCode.valueOf(400), "Account not found"));

        if (candidate.getProfile() != null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Profile already exists");
        }

        candidate.setProfile(Profile.builder().account(candidate).isWorker(isWorker).id(candidate.getId()).build());

        Account accountDb = this.accountService.update(candidate, candidate.getId());
        Profile profileDb = accountDb.getProfile();

        return ProfileCreatedResponse.builder()
                .isWorker(profileDb.isWorker())
                .id(profileDb.getId())
                .build();
    }
}
