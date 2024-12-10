package com.sg.kata.bank.util;


import com.sg.kata.bank.domain.Transaction;

import java.util.List;

public interface IOperationHistoryFormatter {
    List<String> format(List<Transaction> transactions);
}

