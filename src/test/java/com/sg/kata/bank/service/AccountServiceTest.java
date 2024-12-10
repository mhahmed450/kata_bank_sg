package com.sg.kata.bank.service;


import com.sg.kata.bank.domain.Account;
import com.sg.kata.bank.repository.IAccountRepository;
import com.sg.kata.bank.service.impl.AccountService;
import com.sg.kata.bank.util.IOperationHistoryFormatter;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import static org.junit.Assert.assertThrows;


public class AccountServiceTest {

    private IAccountRepository accountRepository;
    private IOperationHistoryFormatter formatter;
    private AccountService accountService;

    @Before
    public void setup() {
        accountRepository = mock(IAccountRepository.class);
        formatter = mock(IOperationHistoryFormatter.class);
        accountService = new AccountService(accountRepository, formatter);
    }
    @Test
    public void should_allow_client_to_deposit_money() {
        // Given
        Account account = new Account("12345");
        when(accountRepository.findById("12345")).thenReturn(Optional.of(account));
        when(accountRepository.save(account)).thenReturn(account);

        // When
        Account updatedAccount = accountService.deposit("12345", BigDecimal.valueOf(1000));

        // Then
        assertEquals(BigDecimal.valueOf(1000), updatedAccount.getBalance());
        verify(accountRepository).save(account);
    }

    @Test
    public void should_allow_client_to_withdraw_money() {
        // Given
        Account account = new Account("12345");
        account.deposit(BigDecimal.valueOf(1000));
        when(accountRepository.findById("12345")).thenReturn(Optional.of(account));
        when(accountRepository.save(account)).thenReturn(account);

        // When
        Account updatedAccount = accountService.withdraw("12345", BigDecimal.valueOf(500));

        // Then
        assertEquals(BigDecimal.valueOf(500), updatedAccount.getBalance());
        verify(accountRepository).save(account);
    }

    @Test
    public void should_return_transaction_history() {
        // Given
        Account account = new Account("12345");
        account.deposit(BigDecimal.valueOf(1000));
        account.withdraw(BigDecimal.valueOf(500));
        when(accountRepository.findById("12345")).thenReturn(Optional.of(account));
        when(formatter.format(account.getTransactions()))
                .thenReturn(List.of(
                        "2024-12-10 | DEPOSIT | 1000.00 | 1000.00",
                        "2024-12-11 | WITHDRAWAL | 500.00 | 500.00"
                ));

        // When
        List<String> statement = accountService.getStatement("12345");

        // Then
        assertEquals(2, statement.size());
        assertEquals("2024-12-10 | DEPOSIT | 1000.00 | 1000.00", statement.get(0));
        assertEquals("2024-12-11 | WITHDRAWAL | 500.00 | 500.00", statement.get(1));
        verify(formatter).format(account.getTransactions());
    }

    @Test
    public void should_throw_exception_when_withdrawing_more_than_balance() {
        // Given
        Account account = new Account("12345");
        account.deposit(BigDecimal.valueOf(500));
        when(accountRepository.findById("12345")).thenReturn(Optional.of(account));

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> accountService.withdraw("12345", BigDecimal.valueOf(1000)));

        assertEquals("Insufficient funds", exception.getMessage());
    }

    @Test
    public void should_throw_exception_when_account_not_found() {
        // Given
        when(accountRepository.findById("12345")).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> accountService.deposit("12345", BigDecimal.valueOf(1000)));

        assertEquals("Account not found: 12345", exception.getMessage());
    }
}
