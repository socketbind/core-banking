package org.gabriel.banking.model;

import org.gabriel.banking.model.exceptions.DepositFailedException;
import org.gabriel.banking.model.exceptions.NoSuchUserException;
import org.gabriel.banking.model.exceptions.TransferFailedException;
import org.gabriel.banking.model.exceptions.WithdrawalFailedException;

import java.math.BigDecimal;
import java.util.List;

public interface BankService {

    User authenticateAs(String username, String password, String otpCode) throws NoSuchUserException;

    List<BankAccount> retrieveAccountsFor(User user);

    Transaction performWithdraw(User user, BankAccount account, BigDecimal amount) throws WithdrawalFailedException;

    Transaction performDeposit(User user, BankAccount account, BigDecimal amount) throws DepositFailedException;

    Transaction performTransfer(User user, BankAccount sourceAccount, BankAccount destinationAccount, BigDecimal amount) throws TransferFailedException;

}
