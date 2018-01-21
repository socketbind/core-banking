package org.gabriel.banking.model;

public class DummyAuthenticator implements Authenticator {
    @Override
    public boolean verifyCredentials(String username, String password, String otpCode) {
        return true;
    }
}
