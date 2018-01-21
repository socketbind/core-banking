package org.gabriel.banking.model;

import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SimpleTransactionsFormatter implements TransactionsFormatter {

    private final DateFormat dateFormatter;

    public SimpleTransactionsFormatter() {
        dateFormatter = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.getDefault());
    }

    public SimpleTransactionsFormatter(String datePattern) {
        dateFormatter = new SimpleDateFormat(datePattern);
    }

    @Override
    public void formatTransactions(List<Transaction> transactions, Predicate<Transaction> filter, PrintStream output) {
        List<Transaction> filtered = transactions.stream().filter(filter).collect(Collectors.toList());

        if (filtered.isEmpty()) {
            output.println("No transactions");
        } else {
            output.printf("%-30s %-30s %-30s %-30s %-30s\n", "FROM", "TO", "DATE", "AMOUNT", "CLOSING BALANCE");
            for (Transaction transaction : filtered) {
                output.printf("%-30s %-30s %-30s %-30s %-30s\n",
                        transaction.getSourceFriendlyName(),
                        transaction.getDestinationFriendlyName(),
                        dateFormatter.format(transaction.getDate()),
                        transaction.getAmount().toPlainString(),
                        transaction.getBalance().toPlainString()
                );
            }
        }
    }



}
