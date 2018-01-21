package org.gabriel.banking.model;

import java.util.HashMap;
import java.util.Map;

public class DummyUserStorage implements UserStorage {

    private final Map<String, User> users = new HashMap<>();

    @Override
    public User findUserByUsername(String username) {
        return users.computeIfAbsent(username, key -> {
            User user = new User();
            user.setUsername(username);
            user.setFirstName("John");
            user.setLastName("Rambo");
            return user;
        });
    }
}
