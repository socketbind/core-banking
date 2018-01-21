package org.gabriel.banking.model;

import java.util.HashMap;
import java.util.Map;

public class DummyUserStorage implements UserStorage {

    private final Map<String, User> users = new HashMap<>();

    private int count = 0;

    @Override
    public User findUserByUsername(String username) {
        return users.computeIfAbsent(username, key -> {
            User user = new User();
            user.setUsername(username);
            user.setFirstName("User");
            user.setLastName("#" + (++count));
            return user;
        });
    }
}
