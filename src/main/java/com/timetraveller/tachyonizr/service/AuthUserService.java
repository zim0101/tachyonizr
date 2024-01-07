package com.timetraveller.tachyonizr.service;

import com.app.realtimesocketionotificationpoc.model.Account;
import com.app.realtimesocketionotificationpoc.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthUserService {
    private final AccountRepository accountRepository;

    public AuthUserService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Account currentAuthAccount() {
        return accountRepository.findByUsername(getAuthentication().getName())
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));
    }

    public boolean authUserIsAdminGroup() {
        Authentication authentication = getAuthentication();
        Set<String> userRoles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toSet());

        List<String> roles = List.of("ROLE_ADMIN");

        log.info("matches: {}", userRoles.stream().anyMatch(roles::contains));
        return userRoles.stream().anyMatch(roles::contains);
    }

}

