package com.damintsev.gwt.common.source.uiexceptions;

/**
 * User: adamintsev
 * Date: 06.03.14
 * //todo написать комментарии
 */
public class CustomUiException extends RuntimeException {

    public CustomUiException() {
    }

    public CustomUiException(String message) {
        super(message);
    }

    public CustomUiException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomUiException(Throwable cause) {
        super(cause);
    }
}
