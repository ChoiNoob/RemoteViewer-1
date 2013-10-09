package com.damintsev.server.v2.v3.exceptions;

/**
 * User: Damintsev Andrey
 * Date: 08.10.13
 * Time: 22:33
 */
public class ConnectException extends Exception {

    public ConnectException() {
        super();
    }

    public ConnectException(Exception e) {
        super(e);
    }
}
