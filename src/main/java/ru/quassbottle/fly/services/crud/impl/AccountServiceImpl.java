package ru.quassbottle.fly.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.quassbottle.fly.entities.Account;
import ru.quassbottle.fly.repositories.AccountRepository;
import ru.quassbottle.fly.services.AccountService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> getAll() {
        return this.accountRepository.findAll();
    }

    @Override
    public Account getById(Long id) {
        return this.accountRepository.getReferenceById(id);
    }

    @Override
    public Account save(Account account) {
        return this.accountRepository.save(account);
    }

    @Override
    public Account update(Account account, Long id) {
        Optional<Account> candidate = this.accountRepository.findById(id);
        if (candidate.isEmpty()) return null;

        Account accountDb = candidate.get();
        if (Objects.nonNull(account.getEmail()) && !"".equalsIgnoreCase(account.getEmail())) {
            accountDb.setEmail(account.getEmail());
        }
        if (Objects.nonNull(account.getHashedPassword()) && !"".equalsIgnoreCase(account.getHashedPassword())) {
            accountDb.setHashedPassword(account.getHashedPassword());
        }
        accountDb.setRole(Math.min(0, account.getRole()));
        return this.accountRepository.save(accountDb);
    }

    @Override
    public void deleteById(Long id) {
        this.accountRepository.deleteById(id);
    }
}
