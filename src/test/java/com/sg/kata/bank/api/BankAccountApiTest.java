package com.sg.kata.bank.api;

import com.sg.kata.bank.service.IAccountService;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class BankAccountApiTest {

    private BankAccountApi api;
    private IAccountService accountService;

    @Before
    public void setup() {
        accountService = mock(IAccountService.class);
        api = new BankAccountApi(accountService);
    }

    @Test
    public void should_deposit_money() {
        // Act
        api.deposit("12345", BigDecimal.valueOf(1000));

        // Assert
        verify(accountService).deposit("12345", BigDecimal.valueOf(1000));
    }

    @Test
    public void should_withdraw_money() {
        // Act
        api.withdraw("12345", BigDecimal.valueOf(500));

        // Assert
        verify(accountService).withdraw("12345", BigDecimal.valueOf(500));
    }

    @Test
    public void should_return_account_statement() {
        // Arrange
        when(accountService.getStatement("12345"))
                .thenReturn(List.of(
                        "2024-12-10 | DEPOSIT | 1000.00 | 1000.00",
                        "2024-12-11 | WITHDRAWAL | 500.00 | 500.00"
                ));

        // Act
        List<String> statement = api.getStatement("12345");

        // Assert
        assertEquals(2, statement.size());
        assertEquals("2024-12-10 | DEPOSIT | 1000.00 | 1000.00", statement.get(0));
        assertEquals("2024-12-11 | WITHDRAWAL | 500.00 | 500.00", statement.get(1));
    }

    @Test
    public void should_print_account_statement() {
        // Arrange
        when(accountService.getStatement("12345"))
                .thenReturn(List.of(
                        "2024-12-10 | DEPOSIT | 1000.00 | 1000.00",
                        "2024-12-11 | WITHDRAWAL | 500.00 | 500.00"
                ));

        // Act
        api.printStatement("12345");

        // Assert
        verify(accountService).getStatement("12345");
    }
}
