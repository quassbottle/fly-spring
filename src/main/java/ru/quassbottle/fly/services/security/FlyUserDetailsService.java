package ru.quassbottle.fly.services.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.quassbottle.fly.entities.Account;
import ru.quassbottle.fly.repositories.AccountRepository;
import ru.quassbottle.fly.services.crud.AccountService;

import java.util.Collection;

@Service
public class FlyUserDetailService implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findFirstByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Email not found")); // todo: refactor
        return new Account(account.getEmail(), account.getHashedPassword(), account.getRoles());
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(Collection)
}
