package org.gabriel.banking;

import org.gabriel.banking.model.*;
import org.gabriel.banking.model.exceptions.DepositFailedException;
import org.gabriel.banking.model.exceptions.NoSuchUserException;
import org.gabriel.banking.model.exceptions.TransferFailedException;
import org.gabriel.banking.model.exceptions.WithdrawalFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class SimpleHarness {

    private static final Logger logger = LoggerFactory.getLogger(SimpleHarness.class);

    public static void main(String[] args) {
        SimpleLedger ledger = new SimpleLedger();

        BankService bankService = new DefaultBankService(
                new DummyAuthenticator(),
                new DummyUserStorage(),
                new DummyAccountsStorage(),
                ledger
        );

        TransactionsFormatter transactionsFormatter = new SimpleTransactionsFormatter();

        try {
            User user1 = bankService.authenticateAs("peti", "somepass", "somecode");
            User user2 = bankService.authenticateAs("gyuri", "somepass", "somecode");
            User user3 = bankService.authenticateAs("rezso", "somepass", "somecode");

            List<BankAccount> user1Accounts = bankService.retrieveAccountsFor(user1);
            List<BankAccount> user2Accounts = bankService.retrieveAccountsFor(user2);
            List<BankAccount> user3Accounts = bankService.retrieveAccountsFor(user3);

            BankAccount user1Account = user1Accounts.get(0);
            BankAccount user2Account = user2Accounts.get(0);
            BankAccount user3Account = user3Accounts.get(0);

            bankService.performWithdraw(user1, user1Account, BigDecimal.valueOf(1000));
            bankService.performDeposit(user1, user1Account, BigDecimal.valueOf(6000));
            bankService.performTransfer(user1, user1Account, user2Account, BigDecimal.valueOf(1500));

            bankService.performTransfer(user3, user3Account, user2Account, BigDecimal.valueOf(1000));

            System.out.println("All transactions");
            transactionsFormatter.formatTransactions(ledger.getTransactions(), System.out);
            System.out.println();

            System.out.println("By " + user1.getFriendlyName());
            transactionsFormatter.formatTransactions(ledger.getTransactions(), TransactionFilters.specificUser(user1), System.out);
            System.out.println();

            LocalDate now = LocalDate.now();
            System.out.println("By " + user1.getFriendlyName() + " on " + now);
            transactionsFormatter.formatTransactions(
                    ledger.getTransactions(),
                    TransactionFilters.specificUser(user1).and(TransactionFilters.specificDate(now)),
                    System.out
            );
        } catch (NoSuchUserException e) {
            logger.error("Login failed", e);
        } catch (WithdrawalFailedException e) {
            logger.error("Withdrawal failed", e);
        } catch (DepositFailedException e) {
            logger.error("Deposit failed", e);
        } catch (TransferFailedException e) {
            logger.error("Transfer failed", e);
        }
    }

}
