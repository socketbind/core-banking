package org.gabriel.banking.model.exceptions;

public class DepositFailedException extends Exception {

    public DepositFailedException() {
    }

    public DepositFailedException(String message) {
        super(message);
    }

    public DepositFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public DepositFailedException(Throwable cause) {
        super(cause);
    }
}
