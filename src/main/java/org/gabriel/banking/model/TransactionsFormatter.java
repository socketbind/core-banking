package org.gabriel.banking.model;

import java.io.PrintStream;
import java.util.List;
import java.util.function.Predicate;

public interface TransactionsFormatter {

    default void formatTransactions(List<Transaction> transactions, PrintStream output) {
        formatTransactions(transactions, TransactionFilters.ALL, output);
    }

    void formatTransactions(List<Transaction> transactions, Predicate<Transaction> filter, PrintStream output);

}
