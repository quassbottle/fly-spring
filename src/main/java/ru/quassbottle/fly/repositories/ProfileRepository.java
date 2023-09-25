package ru.quassbottle.fly.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.quassbottle.fly.entities.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
