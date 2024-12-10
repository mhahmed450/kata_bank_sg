# Kata Bank Account

A simple Java-based application simulating a bank account system. This project follows **Clean Architecture** principles and demonstrates **Behavior-Driven Development (BDD)** using **Cucumber** and **Mockito**.

---

## Features

- **Deposit and Withdrawals**:
    - Save money or retrieve it from your account.
- **Transaction History**:
    - View all operations with their dates, amounts, and balances.
- **BDD Testing & Unit Test**:
    - Scenarios written in Gherkin (`.feature` files) and tested using Cucumber.
    -  Unit test covering all project layers ( except Repo )
    - Integration test for the account service

---

## Requirements

- **Java**
- **Maven**
- **Dependencies**:
    - JUnit
    - Mockito
    - Cucumber

---

## Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/mhahmed450/kata_bank_sg.git
   cd kata-bank-account
   ```

2. Build the project:

   ```bash
   mvn clean install
   ```

---

## Testing

### Run Unit Tests

Execute the following command to run unit tests:

```bash
mvn test
```

### Run BDD Scenarios

Run Cucumber tests with:

```bash
mvn verify
```

Cucumber will execute all `.feature` file in the `src/test/resources` folder and display results in a human-readable format.

---

## Project Structure

```plaintext
src/
├── main/
│   ├── java/
│   │   ├── com.sg.kata.bank/
│   │   │   ├── domain/          # Core domain models like Account and Transaction
│   │   │   ├── repository/      # Repository interface and in-memory implementation
│   │   │   ├── service/         # Business logic for account operations
│   │   │   ├── util/            # Utilities like transaction formatting
│   │   │   ├── Main.java        # Entry point for the application
├── test/
│   ├── java/
│   │   ├── com.sg.kata.bank/
│   │   │   ├── bdd/             # Cucumber step definitions
│   │   │   ├── service/         # Unit tests for services
    |   |   |...
│   ├── resources/
│   │   ├── account_service.feature  # BDD scenarios in Gherkin
```

---
