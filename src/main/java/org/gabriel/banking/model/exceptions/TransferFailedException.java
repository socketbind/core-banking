package org.gabriel.banking.model.exceptions;

public class TransferFailedException extends Exception {

    public TransferFailedException() {
    }

    public TransferFailedException(String message) {
        super(message);
    }

    public TransferFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransferFailedException(Throwable cause) {
        super(cause);
    }
}
