package com.sg.kata.bank.util;

import com.sg.kata.bank.domain.Transaction;
import com.sg.kata.bank.domain.TransactionType;
import com.sg.kata.bank.util.impl.OperationHistoryFormatter;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OperationHistoryTest {

    @Test
    public void should_format_transactions() {
        // Arrange
        IOperationHistoryFormatter printer = new OperationHistoryFormatter();
        List<Transaction> transactions = List.of(
                new Transaction(LocalDate.of(2024, 12, 10), TransactionType.DEPOSIT, BigDecimal.valueOf(1000), BigDecimal.valueOf(1000)),
                new Transaction(LocalDate.of(2024, 12, 11), TransactionType.WITHDRAWAL, BigDecimal.valueOf(500), BigDecimal.valueOf(500))
        );

        // Act
        List<String> formattedTransactions = printer.format(transactions);

        // Assert
        assertEquals(2, formattedTransactions.size());
        assertEquals("2024-12-10 | DEPOSIT | 1000 | 1000", formattedTransactions.get(0));
        assertEquals("2024-12-11 | WITHDRAWAL | 500 | 500", formattedTransactions.get(1));
    }
}
