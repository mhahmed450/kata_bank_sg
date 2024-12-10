Feature: Account Service

  Scenario: Deposit money into an account
    Given an account with ID "12345" exists
    When I deposit 1000 into the account
    Then the account balance should be 1000

  Scenario: Withdraw money from an account
    Given an account with ID "12345" and a balance of 1000 exists
    When I withdraw 400 from the account
    Then the account balance should be 600

  Scenario: Get transaction history
    Given an account with ID "12345" has the following transactions:
      | Type      | Amount | Balance |
      | DEPOSIT   | 1000   | 1000   |
      | WITHDRAWAL| 500    | 500     |
    When I request the transaction history
    Then the history should contain:
      | Type      | Amount | Balance |
      | DEPOSIT   | 1000   | 1000    |
      | WITHDRAWAL| 500    | 500     |

  Scenario: Withdraw more money than available
    Given an account with ID "12345" and a balance of 500 exists
    When I try to withdraw 1000 from the account
    Then an error "Insufficient funds" should be thrown
