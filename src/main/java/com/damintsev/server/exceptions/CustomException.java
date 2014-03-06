package com.damintsev.server.exceptions;

import org.springframework.http.HttpStatus;

/**
 * @author Damintsev Andrey
 *         26.02.14.
 */
public class CustomException extends RuntimeException implements ExceptionMapping {

    protected HttpStatus httpStatus;

    public CustomException() {
    }

    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public CustomException(Throwable cause) {
        super(cause);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus == null ? HttpStatus.INTERNAL_SERVER_ERROR : httpStatus;
    }
}

