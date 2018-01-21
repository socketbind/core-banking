package org.gabriel.banking.model;

import java.util.List;

public interface AccountsStorage {

    List<BankAccount> findByUser(User user);

}
