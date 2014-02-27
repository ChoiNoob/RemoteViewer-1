package com.damintsev.common.exceptions;

/**
 * @author Damintsev Andrey
 *         26.02.14.
 */
public class CustomException extends RuntimeException {

    public CustomException() {
    }

    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomException(Throwable cause) {
        super(cause);
    }
}

