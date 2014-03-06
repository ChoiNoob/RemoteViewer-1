package com.damintsev.server.exceptions;

import org.springframework.http.HttpStatus;

/**
 * User: adamintsev
 * Date: 06.03.14
 * //todo написать комментарии
 */
public interface ExceptionMapping {

    String getMessage();

    HttpStatus getHttpStatus();
}
