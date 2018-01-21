package org.gabriel.banking.model.exceptions;

public class UnauthorizedOperationException extends Exception {
    public UnauthorizedOperationException() {
    }

    public UnauthorizedOperationException(String message) {
        super(message);
    }

    public UnauthorizedOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorizedOperationException(Throwable cause) {
        super(cause);
    }
}
