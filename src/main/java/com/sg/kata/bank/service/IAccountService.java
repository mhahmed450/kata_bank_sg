package com.sg.kata.bank.service;


import com.sg.kata.bank.domain.Account;

import java.math.BigDecimal;
import java.util.List;

public interface IAccountService {
    Account deposit(String accountId, BigDecimal amount);
    Account withdraw(String accountId, BigDecimal amount);
    List<String> getStatement(String accountId);
}
