package org.gabriel.banking.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DummyAccountsStorage implements AccountsStorage {

    private final Map<User, List<BankAccount>> storage = new HashMap<>();

    private int count = 0;

    @Override
    public List<BankAccount> findByUser(User user) {
        return storage.computeIfAbsent(user, key -> {
            List<BankAccount> accounts = new ArrayList<>();
            BankAccount firstAccount = new BankAccount();

            firstAccount.setUser(key);
            firstAccount.setBalance(new BigDecimal(5000 + (count++) * 1000));

            accounts.add(firstAccount);
            return accounts;
        });
    }
}
