package org.gabriel.banking.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class DummyAccountsStorage implements AccountsStorage {

    private final Map<User, BankAccount> storage = new HashMap<>();

    private int count = 0;

    @Override
    public BankAccount findByUser(User user) {
        return storage.computeIfAbsent(user, key -> {
            BankAccount firstAccount = new BankAccount();
            firstAccount.setUser(key);
            firstAccount.setBalance(new BigDecimal(5000 + (count++) * 1000));
            return firstAccount;
        });
    }
}
