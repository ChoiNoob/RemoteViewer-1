package com.damintsev.server.v2.v3.exceptions;

/**
 * User: Damintsev Andrey
 * Date: 08.10.13
 * Time: 22:33
 */
public class ConnectionException extends Exception {

    public ConnectionException() {
        super();
    }

    public ConnectionException(Exception e) {
        super(e);
    }

    public ConnectionException(String e) {
        super(e);
    }
}
