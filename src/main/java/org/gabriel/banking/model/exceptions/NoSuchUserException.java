package org.gabriel.banking.model.exceptions;

public class NoSuchUserException extends Exception {

    private final String username;

    public NoSuchUserException(String username) {
        super("No such user: " + username);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}
