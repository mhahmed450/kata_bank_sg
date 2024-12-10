package com.sg.kata.bank.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Account {

    private final String id;
    private BigDecimal balance = BigDecimal.ZERO;
    private final List<Transaction> transactions = new ArrayList<>();

    public Account(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Account ID cannot be null or empty");
        }
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        // Return an unmodifiable list to protect internal state
        return Collections.unmodifiableList(transactions);
    }

    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than zero");
        }
        balance = balance.add(amount);
        transactions.add(new Transaction(
                java.time.LocalDate.now(),
                TransactionType.DEPOSIT,
                amount,
                balance
        ));
    }

    public void withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be greater than zero");
        }
        if (amount.compareTo(balance) > 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        balance = balance.subtract(amount);
        transactions.add(new Transaction(
                java.time.LocalDate.now(),
                TransactionType.WITHDRAWAL,
                amount,
                balance
        ));
    }
}
