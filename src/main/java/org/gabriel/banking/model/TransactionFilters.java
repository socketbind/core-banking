package org.gabriel.banking.model;

import java.util.function.Predicate;

public final class TransactionFilters {

    public static final Predicate<Transaction> ALL = transaction -> true;

    public static final Predicate<Transaction> WITHDRAWALS = transaction -> transaction.getType().equals(TransactionType.WITHDRAWAL);

    public static final Predicate<Transaction> DEPOSITS = transaction -> transaction.getType().equals(TransactionType.DEPOSIT);

    public static final Predicate<Transaction> TRANSFERS = transaction -> transaction.getType().equals(TransactionType.DEPOSIT);

    private TransactionFilters() {}

    public static Predicate<Transaction> specificUser(User user) {
        return transaction -> {
            if (transaction.getSourceAccount() != null && transaction.getSourceAccount().getUser().getUsername().equals(user.getUsername())) {
                return true;
            }

            if (transaction.getDestinationAccount() != null && transaction.getDestinationAccount().getUser().getUsername().equals(user.getUsername())) {
                return true;
            }

            return false;
        };
    }

}
