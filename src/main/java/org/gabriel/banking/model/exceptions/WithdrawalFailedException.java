package org.gabriel.banking.model.exceptions;

public class WithdrawalFailedException extends Exception {

    public WithdrawalFailedException() {
    }

    public WithdrawalFailedException(String message) {
        super(message);
    }

    public WithdrawalFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public WithdrawalFailedException(Throwable cause) {
        super(cause);
    }
}
