package ru.quassbottle.fly.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.quassbottle.fly.entities.Offer;
import ru.quassbottle.fly.entities.Profile;
import ru.quassbottle.fly.repositories.ProfileRepository;
import ru.quassbottle.fly.services.ProfileService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;

    @Autowired
    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public List<Profile> getAll() {
        return this.profileRepository.findAll();
    }

    @Override
    public Profile getById(Long id) {
        return this.profileRepository.getReferenceById(id);
    }

    @Override
    public Profile save(Profile profile) {
        return this.profileRepository.save(profile);
    }

    @Override
    public Profile update(Profile profile, Long id) {
        Optional<Profile> candidate = this.profileRepository.findById(id);
        if (candidate.isEmpty()) return null;

        Profile profileDb = candidate.get();

        if (Objects.nonNull(profile.getFirstname()) && !profile.getFirstname().equalsIgnoreCase("")) {
            profileDb.setFirstname(profile.getFirstname());
        }

        if (Objects.nonNull(profile.getLastname()) && !profile.getLastname().equalsIgnoreCase("")) {
            profileDb.setLastname(profile.getLastname());
        }

        profileDb.setWorker(profile.isWorker());

        return this.profileRepository.save(profileDb);
    }

    @Override
    public void deleteById(Long id) {
        this.profileRepository.deleteById(id);
    }
}
