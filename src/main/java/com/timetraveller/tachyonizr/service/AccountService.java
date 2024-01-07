package com.timetraveller.tachyonizr.service;

import com.app.realtimesocketionotificationpoc.model.Account;
import com.app.realtimesocketionotificationpoc.model.enums.Role;
import com.app.realtimesocketionotificationpoc.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final NotificationHistoryService notificationHistoryService;

    public AccountService(AccountRepository accountRepository,
                          PasswordEncoder passwordEncoder,
                          NotificationHistoryService notificationHistoryService) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.notificationHistoryService = notificationHistoryService;
    }

    public Account findById(Integer id) {
        return accountRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Account not found!"));
    }

    public Account findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Transactional
    public void saveAccount(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        if (account.getRoles() == null) {
            account.setRoles(Set.of(Role.USER));
        }
        accountRepository.save(account);
        notificationHistoryService.saveAndSendUserRegistrationNotificationToAdmin(getListOfAdminAccount(), account);
    }

    public List<Account> getListOfAdminAccount() {
        return accountRepository.findByRole(Role.ADMIN);
    }
}
