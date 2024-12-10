package com.sg.kata.bank.util.impl;


import com.sg.kata.bank.domain.Transaction;
import com.sg.kata.bank.util.IOperationHistoryFormatter;

import java.util.List;
import java.util.stream.Collectors;


public class OperationHistoryFormatter implements IOperationHistoryFormatter {

    private static final String HEADER = "DATE | TYPE | AMOUNT | BALANCE";

    @Override
    public List<String> format(List<Transaction> transactions) {
        // Convert transactions to formatted strings
        return transactions.stream()
                .map(transaction -> String.format(
                        "%s | %s | %s | %s",
                        transaction.getDate(),
                        transaction.getType(),
                        transaction.getAmount(),
                        transaction.getBalanceAfterTransaction()
                ))
                .collect(Collectors.toList());
    }
}