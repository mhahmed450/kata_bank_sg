package com.sg.kata.bank.domain;


import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class AccountTest {

    private Account account;

    @Before
    public void setup() {
        account = new Account("12345");
    }

    @Test
    public void should_deposit_money() {
        account.deposit(BigDecimal.valueOf(1000));
        assertEquals(BigDecimal.valueOf(1000), account.getBalance());
    }

    @Test
    public void should_withdraw_money() {
        account.deposit(BigDecimal.valueOf(1000));
        account.withdraw(BigDecimal.valueOf(500));
        assertEquals(BigDecimal.valueOf(500), account.getBalance());
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_exception_for_insufficient_funds() {
        account.withdraw(BigDecimal.valueOf(500));
    }

    @Test
    public void should_track_transactions() {
        account.deposit(BigDecimal.valueOf(1000));
        account.withdraw(BigDecimal.valueOf(500));

        assertEquals(2, account.getTransactions().size());

        Transaction depositTransaction = account.getTransactions().get(0);
        assertEquals(BigDecimal.valueOf(1000), depositTransaction.getAmount());
        assertEquals(BigDecimal.valueOf(1000), depositTransaction.getBalanceAfterTransaction());

        Transaction withdrawalTransaction = account.getTransactions().get(1);
        assertEquals(BigDecimal.valueOf(500), withdrawalTransaction.getAmount());
        assertEquals(BigDecimal.valueOf(500), withdrawalTransaction.getBalanceAfterTransaction());
    }
}
