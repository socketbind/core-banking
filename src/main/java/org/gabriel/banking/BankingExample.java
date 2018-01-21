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

    private void runExample() {
        try {
            User user1 = bankService.authenticateAs("peti", "somepass", "somecode");
            User user2 = bankService.authenticateAs("gyuri", "somepass", "somecode");
            User user3 = bankService.authenticateAs("rezso", "somepass", "somecode");

            BankAccount user1Account = bankService.retrieveAccountFor(user1);
            BankAccount user2Account = bankService.retrieveAccountFor(user2);
            BankAccount user3Account = bankService.retrieveAccountFor(user3);

            System.out.println(user1Account);
            System.out.println(user2Account);
            System.out.println(user3Account);
            System.out.println();

            try {
                bankService.performWithdraw(user1, user1Account, BigDecimal.valueOf(1000));
            } catch (WithdrawalFailedException e) {
                logger.error("Withdraw failed", e);
            }

            try {
                bankService.performDeposit(user1, user1Account, BigDecimal.valueOf(6000));
            } catch (DepositFailedException e) {
                logger.error("Deposit failed", e);
            }

            try {
                bankService.performTransfer(user1, user1Account, user2Account, BigDecimal.valueOf(1500));
            } catch (TransferFailedException e) {
                logger.error("Transfer failed", e);
            }

            try {
                bankService.performTransfer(user3, user3Account, user2Account, BigDecimal.valueOf(1000));
            } catch (TransferFailedException e) {
                logger.error("Transfer failed", e);
            }

            // Examples for failing transactions
            try {
                bankService.performWithdraw(user1, user1Account, BigDecimal.valueOf(10000));
            } catch (WithdrawalFailedException e) {
                logger.error("Withdraw failed", e);
            }

            try {
                bankService.performTransfer(user1, user1Account, user2Account, BigDecimal.valueOf(150000));
            } catch (TransferFailedException e) {
                logger.error("Transfer failed", e);
            }

            /*
                Closing balance can be a little bit confusing when listing all transactions, in case of withdrawals and
                transfers the balance refers to the account from which the amount was deducted. In case of deposits the
                benefiting account balance is shown.
             */
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

            System.out.println(user1Account);
            System.out.println(user2Account);
            System.out.println(user3Account);
        } catch (NoSuchUserException e) {
            logger.error("Login failed", e);
        }
    }

    public static void main(String[] args) {
        new BankingExample().runExample();
    }

}
