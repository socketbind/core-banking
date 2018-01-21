package org.gabriel.banking.model;

import java.math.BigDecimal;

public class BankAccount {

    private User user;

    private BigDecimal balance;

    BankAccount() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "user=" + user +
                ", balance=" + balance +
                '}';
    }
}
