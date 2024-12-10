package com.sg.kata.bank.repository;

import com.sg.kata.bank.domain.Account;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryAccountRepository implements IAccountRepository {

    private final Map<String, Account> accounts = new HashMap<>();

    @Override
    public Account save(Account account) {
      return accounts.put(account.getId(), account);
    }

    @Override
    public Optional<Account> findById(String accountId) {
        return Optional.ofNullable(accounts.get(accountId));
    }
}
