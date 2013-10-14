package com.damintsev.server.v2.v3.exceptions;

/**
 * User: Damintsev Andrey
 * Date: 08.10.13
 * Time: 22:35
 */
public class ExecutingTaskException extends Exception {

    public ExecutingTaskException() {
    }

    public ExecutingTaskException(Throwable cause) {
        super(cause);
    }

    public ExecutingTaskException(String message) {
        super(message);
    }
}
