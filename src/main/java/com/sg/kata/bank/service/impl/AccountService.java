package com.sg.kata.bank.service.impl;


import com.sg.kata.bank.domain.Account;

import java.math.BigDecimal;
import java.util.List;

import com.sg.kata.bank.repository.IAccountRepository;
import com.sg.kata.bank.service.IAccountService;
import com.sg.kata.bank.util.IOperationHistoryFormatter;

public class AccountService implements IAccountService {

    private final IAccountRepository accountRepository;
    private final IOperationHistoryFormatter printer;

    public AccountService(IAccountRepository accountRepository, IOperationHistoryFormatter printer) {
        this.accountRepository = accountRepository;
        this.printer = printer;
    }

    @Override
    public Account deposit(String accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + accountId));
        account.deposit(amount);
        return accountRepository.save(account);
    }

    @Override
    public Account withdraw(String accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + accountId));
        account.withdraw(amount);
        return accountRepository.save(account);
    }

    @Override
    public List<String> getStatement(String accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + accountId));
        return printer.format(account.getTransactions());
    }
}