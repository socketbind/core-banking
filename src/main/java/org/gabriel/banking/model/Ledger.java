package org.gabriel.banking.model;

import org.gabriel.banking.model.exceptions.InsufficientFundsException;

public interface Ledger {

    void performTransaction(Transaction transaction) throws InsufficientFundsException;

}
