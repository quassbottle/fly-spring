package ru.quassbottle.fly.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.quassbottle.fly.entities.Account;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findFirstByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
