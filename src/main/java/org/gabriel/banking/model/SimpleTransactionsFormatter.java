package org.gabriel.banking.model;

import java.io.PrintStream;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SimpleTransactionsFormatter implements TransactionsFormatter {

    private final DateTimeFormatter dateFormatter;

    public SimpleTransactionsFormatter() {
        dateFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
    }

    public SimpleTransactionsFormatter(DateTimeFormatter dateFormatter) {
        this.dateFormatter = dateFormatter;
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
                        transaction.getDate().format(dateFormatter),
                        transaction.getAmount().toPlainString(),
                        transaction.getBalance().toPlainString()
                );
            }
        }
    }



}
