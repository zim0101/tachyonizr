package com.timetraveller.tachyonizr.security;

import com.app.realtimesocketionotificationpoc.model.Account;
import com.app.realtimesocketionotificationpoc.repository.AccountRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {
    private final AccountRepository accountRepository;

    public AppUserDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(usernameOrEmail).orElseGet(
                () -> accountRepository.findByEmail(usernameOrEmail));

        if (account == null) {
            throw new UsernameNotFoundException("User " + usernameOrEmail + "is not found");
        }
        String[] roles = account.getRoles()
                .stream().map(Enum::name).toArray(String[]::new);

        return User.withUsername(account.getUsername())
                .password(account.getPassword())
                .roles(roles)
                .build();
    }
}
