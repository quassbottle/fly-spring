package ru.quassbottle.fly.services.crud;

import ru.quassbottle.fly.entities.Account;

import java.util.Optional;

public interface AccountService extends BaseCrudService<Account, Long> {
    Optional<Account> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
