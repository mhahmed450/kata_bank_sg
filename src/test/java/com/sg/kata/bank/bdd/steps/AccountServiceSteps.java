package com.sg.kata.bank.bdd.steps;

import com.sg.kata.bank.domain.Account;
import com.sg.kata.bank.repository.IAccountRepository;
import com.sg.kata.bank.service.impl.AccountService;
import com.sg.kata.bank.util.IOperationHistoryFormatter;
import com.sg.kata.bank.util.impl.OperationHistoryFormatter;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountServiceSteps {

    private IAccountRepository accountRepository;
    private IOperationHistoryFormatter formatter;
    private AccountService accountService;
    private Account account;
    private Exception exception;

    @Given("an account with ID {string} exists")
    public void an_account_with_id_exists(String accountId) {
        account = new Account(accountId);
        accountRepository = mock(IAccountRepository.class);
        formatter = new OperationHistoryFormatter();
        accountService = new AccountService(accountRepository, formatter);
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountRepository.save(account)).thenReturn(account);
    }

    @Given("an account with ID {string} and a balance of {int} exists")
    public void an_account_with_id_and_a_balance_of_exists(String accountId, int balance) {
        an_account_with_id_exists(accountId);
        account.deposit(BigDecimal.valueOf(balance));
    }

    @Given("an account with ID {string} has the following transactions:")
    public void an_account_with_id_has_the_following_transactions(String accountId, List<List<String>> transactions) {
        an_account_with_id_exists(accountId);

        // Ignore the first row (header)
        for (int i = 1; i < transactions.size(); i++) {
            List<String> transaction = transactions.get(i);
            String type = transaction.get(0).trim(); // delete spaces
            BigDecimal amount = new BigDecimal(transaction.get(1).trim());

            if (type.equalsIgnoreCase("DEPOSIT")) {
                account.deposit(amount);
            } else if (type.equalsIgnoreCase("WITHDRAWAL")) {
                account.withdraw(amount);
            } else {
                throw new IllegalArgumentException("Unknown transaction type: " + type);
            }
        }
    }
    @When("I deposit {int} into the account")
    public void i_deposit_into_the_account(int amount) {
        accountService.deposit(account.getId(), BigDecimal.valueOf(amount));
    }

    @When("I withdraw {int} from the account")
    public void i_withdraw_from_the_account(int amount) {
        accountService.withdraw(account.getId(), BigDecimal.valueOf(amount));
    }

    @When("I try to withdraw {int} from the account")
    public void i_try_to_withdraw_from_the_account(int amount) {
        exception = assertThrows(IllegalArgumentException.class, () ->
                accountService.withdraw(account.getId(), BigDecimal.valueOf(amount))
        );
    }

    @When("I request the transaction history")
    public void i_request_the_transaction_history() {
        formatter.format(account.getTransactions());
    }

    @Then("the account balance should be {int}")
    public void the_account_balance_should_be(int expectedBalance) {
        assertEquals(BigDecimal.valueOf(expectedBalance), account.getBalance());
    }

    @Then("the history should contain:")
    public void the_history_should_contain(List<List<String>> expectedTransactions) {
        List<String> formattedHistory = formatter.format(account.getTransactions());
        for (int i = 1; i < expectedTransactions.size(); i++) {
            String expectedLine = String.join(" | ", expectedTransactions.get(i));
            assertEquals(expectedLine, formattedHistory.get(i-1).substring( formattedHistory.get(i-1).indexOf('|') + 2));
        }
    }

    @Then("an error {string} should be thrown")
    public void an_error_should_be_thrown(String expectedMessage) {
        assertEquals(expectedMessage, exception.getMessage());
    }
}
