package org.gabriel.banking.model;

public interface AccountsStorage {

    BankAccount findByUser(User user);

}
