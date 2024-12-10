package com.sg.kata.bank.repository;

import com.sg.kata.bank.domain.Account;

import java.util.Optional;

public interface IAccountRepository {
    Account save(Account account);
    Optional<Account> findById(String accountId);
}