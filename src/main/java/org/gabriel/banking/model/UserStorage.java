package org.gabriel.banking.model;

public interface UserStorage {

    User findUserByUsername(String username);

}
