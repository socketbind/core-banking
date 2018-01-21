package org.gabriel.banking.model;

import org.gabriel.banking.model.exceptions.InsufficientFundsException;

import java.math.BigDecimal;
import java.util.Date;

public class Transaction {

    private final TransactionType type;

    private final BankAccount sourceAccount;

    private final BankAccount destinationAccount;

    private final BigDecimal amount;

    private final BigDecimal sourceBalance;

    private final BigDecimal destinationBalance;

    private Date date;

    Transaction(TransactionType type, BankAccount sourceAccount, BankAccount destinationAccount, BigDecimal amount, BigDecimal sourceBalance, BigDecimal destinationBalance) {
        this.type = type;
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.amount = amount;
        this.sourceBalance = sourceBalance;
        this.destinationBalance = destinationBalance;
    }

    public TransactionType getType() {
        return type;
    }

    public BankAccount getSourceAccount() {
        return sourceAccount;
    }

    public BankAccount getDestinationAccount() {
        return destinationAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getSourceBalance() {
        return sourceBalance;
    }

    public BigDecimal getDestinationBalance() {
        return destinationBalance;
    }

    public Date getDate() {
        return date;
    }

    public BigDecimal getBalance() {
        switch (type) {
            case WITHDRAWAL:
            case TRANSFER:
                return sourceBalance;
            case DEPOSIT:
                return destinationBalance;
        }

        return sourceBalance;
    }

    public String getSourceFriendlyName() {
        if (sourceAccount != null) {
            return sourceAccount.getUser().getFriendlyName();
        } else {
            return type.toString();
        }
    }

    public String getDestinationFriendlyName() {
        if (destinationAccount != null) {
            return destinationAccount.getUser().getFriendlyName();
        } else {
            return type.toString();
        }
    }

    public void apply() throws InsufficientFundsException {
        if (sourceBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientFundsException(String.format("Unable to %s insufficient balance", type));
        }

        if (sourceAccount != null) {
            sourceAccount.setBalance(sourceBalance);
        }

        if (destinationAccount != null) {
            destinationAccount.setBalance(destinationBalance);
        }

        date = new Date();
    }

    public static Transaction withdrawal(BankAccount bankAccount, BigDecimal amount) {
        return new Transaction(
                TransactionType.WITHDRAWAL,
                bankAccount,
                null,
                amount,
                bankAccount.getBalance().subtract(amount),
                amount
        );
    }

    public static Transaction deposit(BankAccount bankAccount, BigDecimal amount) {
        return new Transaction(
                TransactionType.DEPOSIT,
                null,
                bankAccount,
                amount,
                amount,
                bankAccount.getBalance().add(amount)
        );
    }

    public static Transaction transfer(BankAccount sourceAccount, BankAccount destinationAccount, BigDecimal amount) {
        return new Transaction(
                TransactionType.TRANSFER,
                sourceAccount,
                destinationAccount,
                amount,
                sourceAccount.getBalance().subtract(amount),
                destinationAccount.getBalance().add(amount)
        );
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "type=" + type +
                ", sourceAccount=" + sourceAccount +
                ", destinationAccount=" + destinationAccount +
                ", amount=" + amount +
                ", sourceBalance=" + sourceBalance +
                ", destinationBalance=" + destinationBalance +
                '}';
    }

}
