package org.gabriel.banking.model;

import org.gabriel.banking.model.exceptions.*;

import java.math.BigDecimal;

public class DefaultBankService implements BankService {

    private final Authenticator authenticator;

    private final UserStorage userStorage;

    private final AccountsStorage accountsStorage;

    private final Ledger ledger;

    public DefaultBankService(Authenticator authenticator, UserStorage userStorage, AccountsStorage accountsStorage, Ledger ledger) {
        this.authenticator = authenticator;
        this.userStorage = userStorage;
        this.accountsStorage = accountsStorage;
        this.ledger = ledger;
    }

    @Override
    public User authenticateAs(String username, String password, String otpCode) throws NoSuchUserException {
        if (authenticator.verifyCredentials(username, password, otpCode)) {
            return userStorage.findUserByUsername(username);
        }

        throw new NoSuchUserException("No such user: " + username);
    }

    @Override
    public BankAccount retrieveAccountFor(User user) {
        return accountsStorage.findByUser(user);
    }

    @Override
    public Transaction performWithdraw(User user, BankAccount account, BigDecimal amount) throws WithdrawalFailedException {
        if (!user.doesOwn(account)) {
            throw new WithdrawalFailedException("Unable to withdraw", new UnauthorizedOperationException());
        }

        try {
            Transaction withdrawal = Transaction.withdrawal(account, amount);
            ledger.performTransaction(withdrawal);
            return withdrawal;
        } catch (InsufficientFundsException e) {
            throw new WithdrawalFailedException("Unable to withdraw", e);
        }
    }

    @Override
    public Transaction performDeposit(User user, BankAccount account, BigDecimal amount) throws DepositFailedException {
        if (!user.doesOwn(account)) {
            throw new DepositFailedException("Unable to deposit", new UnauthorizedOperationException());
        }

        try {
            Transaction deposit = Transaction.deposit(account, amount);
            ledger.performTransaction(deposit);
            return deposit;
        } catch (InsufficientFundsException e) {
            throw new DepositFailedException("Unable to deposit", e);
        }
    }

    @Override
    public Transaction performTransfer(User user, BankAccount sourceAccount, BankAccount destinationAccount, BigDecimal amount) throws TransferFailedException {
        if (!user.doesOwn(sourceAccount)) {
            throw new TransferFailedException("Unable to deposit", new UnauthorizedOperationException());
        }

        try {
            Transaction transfer = Transaction.transfer(sourceAccount, destinationAccount, amount);
            ledger.performTransaction(transfer);
            return transfer;
        } catch (InsufficientFundsException e) {
            throw new TransferFailedException("Unable to transfer", e);
        }
    }

}
