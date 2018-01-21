package org.gabriel.banking.model;

public interface Authenticator {

    boolean verifyCredentials(String username, String password, String otpCode);

}
