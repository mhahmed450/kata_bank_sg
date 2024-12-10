package com.sg.kata.bank.api;

import com.sg.kata.bank.service.IAccountService;

import java.math.BigDecimal;
import java.util.List;

public class BankAccountApi {

    private final IAccountService accountService;


    public BankAccountApi(IAccountService accountService) {
        // Initialize dependencies
        this.accountService = accountService;

    }

    /**
     * Deposit money into the account.
     *
     * @param accountId The ID of the account.
     * @param amount    The amount to deposit.
     */
    public void deposit(String accountId, BigDecimal amount) {
        accountService.deposit(accountId, amount);
    }

    /**
     * Withdraw money from the account.
     *
     * @param accountId The ID of the account.
     * @param amount    The amount to withdraw.
     */
    public void withdraw(String accountId, BigDecimal amount) {
        accountService.withdraw(accountId, amount);
    }

    /**
     * Get the formatted account statement.
     *
     * @param accountId The ID of the account.
     * @return A list of formatted transactions.
     */
    public List<String> getStatement(String accountId) {
        return accountService.getStatement(accountId);
    }

    /**
     * Print the account statement to the console.
     *
     * @param accountId The ID of the account.
     */
    public void printStatement(String accountId) {
        List<String> statement = getStatement(accountId);
        statement.forEach(System.out::println);
    }
}