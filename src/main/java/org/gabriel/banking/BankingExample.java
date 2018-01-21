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

public class BankingExample {

    private final Logger logger = LoggerFactory.getLogger(BankingExample.class);

    private final SimpleLedger ledger = new SimpleLedger();

    private final BankService bankService = new DefaultBankService(
            new DummyAuthenticator(),
            new DummyUserStorage(),
            new DummyAccountsStorage(),
            ledger
    );

    private final TransactionsFormatter transactionsFormatter = new SimpleTransactionsFormatter();

    private void printAccounts(User... users) {
        for (User user : users) {
            List<BankAccount> userAccounts = bankService.retrieveAccountsFor(user);
            for (BankAccount userAccount : userAccounts) {
                System.out.println(userAccount);
            }
        }
    }

    private void runExample() {
        try {
            User user1 = bankService.authenticateAs("peti", "somepass", "somecode");
            User user2 = bankService.authenticateAs("gyuri", "somepass", "somecode");
            User user3 = bankService.authenticateAs("rezso", "somepass", "somecode");

            List<BankAccount> user1Accounts = bankService.retrieveAccountsFor(user1);
            List<BankAccount> user2Accounts = bankService.retrieveAccountsFor(user2);
            List<BankAccount> user3Accounts = bankService.retrieveAccountsFor(user3);

            printAccounts(user1, user2, user3);
            System.out.println();

            BankAccount user1Account = user1Accounts.get(0);
            BankAccount user2Account = user2Accounts.get(0);
            BankAccount user3Account = user3Accounts.get(0);

            try {
                bankService.performWithdraw(user1, user1Account, BigDecimal.valueOf(1000));
            } catch (WithdrawalFailedException e) {
                logger.error("Withdraw failed", e);
            }

            try {
                bankService.performDeposit(user1, user1Account, BigDecimal.valueOf(6000));
            } catch (DepositFailedException e) {
                e.printStackTrace();
            }

            try {
                bankService.performTransfer(user1, user1Account, user2Account, BigDecimal.valueOf(1500));
            } catch (TransferFailedException e) {
                e.printStackTrace();
            }

            try {
                bankService.performTransfer(user3, user3Account, user2Account, BigDecimal.valueOf(1000));
            } catch (TransferFailedException e) {
                e.printStackTrace();
            }

            System.out.println("All transactions");
            transactionsFormatter.formatTransactions(ledger.getTransactions(), System.out);
            System.out.println();

            System.out.println("By " + user1.getFriendlyName());
            transactionsFormatter.formatTransactions(ledger.getTransactions(), TransactionFilters.specificUser(user1), System.out);
            System.out.println();

            System.out.println("By " + user3.getFriendlyName());
            transactionsFormatter.formatTransactions(ledger.getTransactions(), TransactionFilters.specificUser(user3), System.out);
            System.out.println();

            LocalDate now = LocalDate.now();
            System.out.println("By " + user1.getFriendlyName() + " on " + now);
            transactionsFormatter.formatTransactions(
                    ledger.getTransactions(),
                    TransactionFilters.specificUser(user1).and(TransactionFilters.specificDate(now)),
                    System.out
            );
            System.out.println();

            printAccounts(user1, user2, user3);
            System.out.println();
        } catch (NoSuchUserException e) {
            logger.error("Login failed", e);
        }
    }

    public static void main(String[] args) {
        new BankingExample().runExample();
    }

}
