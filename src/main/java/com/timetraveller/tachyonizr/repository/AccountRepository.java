package com.timetraveller.tachyonizr.repository;

import com.app.realtimesocketionotificationpoc.model.Account;
import com.app.realtimesocketionotificationpoc.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByUsername(String username);

    Account findByEmail(String email);

    @Query("SELECT a FROM Account a JOIN a.roles r WHERE r = :role")
    List<Account> findByRole(@Param("role") Role role);
}