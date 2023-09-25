package ru.quassbottle.fly.services.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.quassbottle.fly.entities.Account;
import ru.quassbottle.fly.entities.Role;
import ru.quassbottle.fly.repositories.AccountRepository;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FlyUserDetailsService implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findFirstByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Email not found")); // todo: refactor
        return new User(account.getEmail(), account.getHashedPassword(), account.getRoles());
    }
}
