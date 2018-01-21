package org.gabriel.banking.model;

import org.gabriel.banking.model.exceptions.InsufficientFundsException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleLedger implements Ledger {

    private final List<Transaction> transactions = new ArrayList<>();

    public void performTransaction(Transaction transaction) throws InsufficientFundsException {
        transaction.apply();
        transactions.add(transaction);
    }

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

}
