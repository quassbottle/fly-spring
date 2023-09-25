package ru.quassbottle.fly.services.crud.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.quassbottle.fly.entities.Account;
import ru.quassbottle.fly.entities.Profile;
import ru.quassbottle.fly.repositories.AccountRepository;
import ru.quassbottle.fly.repositories.ProfileRepository;
import ru.quassbottle.fly.services.crud.ProfileService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public ProfileServiceImpl(ProfileRepository profileRepository, AccountRepository accountRepository) {
        this.profileRepository = profileRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Profile> getAll() {
        return this.profileRepository.findAll();
    }

    @Override
    public Profile getById(Long id) {
        return this.profileRepository.findById(id).orElseThrow();
    }

    @Override
    public Profile save(Profile profile) {
        //return this.profileRepository.save(profile);
        /*return this.accountRepository.findById(profile.getId()).map(account -> {
            account.setProfile(profile);
            this.accountRepository.save(account);
        });*/
        Optional<Account> candidate = this.accountRepository.findById(profile.getId());
        if (candidate.isEmpty()) return null;

        Account accountDb = candidate.get();
        accountDb.setProfile(profile);

        this.accountRepository.save(accountDb);
        return this.profileRepository.save(profile);
    }

    @Override
    public Profile update(Profile profile, Long id) {
        Optional<Profile> candidate = this.profileRepository.findById(id);
        if (candidate.isEmpty()) return null;

        Profile profileDb = candidate.get();

        profileDb.setWorker(profile.isWorker());

        return this.profileRepository.save(profileDb);
    }

    @Override
    public void deleteById(Long id) {
        this.profileRepository.deleteById(id);
    }
}
