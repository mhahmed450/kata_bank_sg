package com.sg.kata.bank.domain;


import java.math.BigDecimal;
import java.time.LocalDate;

public class Transaction {

    private final LocalDate date;
    private final TransactionType type;
    private final BigDecimal amount;
    private final BigDecimal balanceAfterTransaction;

    public Transaction(LocalDate date, TransactionType type, BigDecimal amount, BigDecimal balanceAfterTransaction) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transaction amount must be greater than zero");
        }
        this.date = date;
        this.type = type;
        this.amount = amount;
        this.balanceAfterTransaction = balanceAfterTransaction;
    }

    public LocalDate getDate() {
        return date;
    }

    public TransactionType getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getBalanceAfterTransaction() {
        return balanceAfterTransaction;
    }

    @Override
    public String toString() {
        return date + " | " + type + " | " + amount + " | " + balanceAfterTransaction;
    }
}

