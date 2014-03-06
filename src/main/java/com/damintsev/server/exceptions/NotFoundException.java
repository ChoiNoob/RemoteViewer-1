package com.damintsev.server.exceptions;

import org.springframework.http.HttpStatus;

/**
 * User: adamintsev
 * Date: 06.03.14
 * //todo написать комментарии
 */
public class NotFoundException extends CustomException {

    public NotFoundException() {
        httpStatus = HttpStatus.NOT_FOUND;
    }

    public NotFoundException(String message) {
        super(message);
        httpStatus = HttpStatus.NOT_FOUND;
    }

}
