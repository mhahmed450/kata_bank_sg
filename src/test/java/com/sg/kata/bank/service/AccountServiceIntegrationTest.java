package com.sg.kata.bank.service;

import com.sg.kata.bank.domain.Account;
import com.sg.kata.bank.repository.IAccountRepository;
import com.sg.kata.bank.repository.InMemoryAccountRepository;
import com.sg.kata.bank.service.impl.AccountService;
import com.sg.kata.bank.util.IOperationHistoryFormatter;
import com.sg.kata.bank.util.impl.OperationHistoryFormatter;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AccountServiceIntegrationTest {

    private IAccountRepository accountRepository;
    private IOperationHistoryFormatter formatter;
    private AccountService accountService;
    // Format for the date
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    @Before
    public void setup() {
        accountRepository = new InMemoryAccountRepository();
        formatter = new OperationHistoryFormatter();
        accountService = new AccountService(accountRepository, formatter);

        // Prepopulate the repository with an account
        Account account = new Account("12345");
        accountRepository.save(account);
    }

    @Test
    public void should_deposit_and_withdraw_money_and_generate_correct_statement() {
        // Get the current date in the desired format
        String today = LocalDate.now().format(DATE_FORMAT);
        // Deposit money into the account
        accountService.deposit("12345", BigDecimal.valueOf(1000));
        accountService.deposit("12345", BigDecimal.valueOf(500));

        // Withdraw money from the account
        accountService.withdraw("12345", BigDecimal.valueOf(400));

        // Get the formatted statement
        List<String> statement = accountService.getStatement("12345");

        // Verify the transactions and balances
        assertEquals(3, statement.size());
        assertEquals(today + " | DEPOSIT | 1000 | 1000", statement.get(0));
        assertEquals(today + " | DEPOSIT | 500 | 1500", statement.get(1));
        assertEquals(today + " | WITHDRAWAL | 400 | 1100", statement.get(2));

        // Verify the final balance
        Account account = accountRepository.findById("12345").orElseThrow();
        assertEquals(BigDecimal.valueOf(1100), account.getBalance());
    }
}
