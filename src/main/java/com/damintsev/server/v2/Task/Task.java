package com.damintsev.server.v2.Task;

import com.damintsev.server.v2.connection.Connection;

/**
 * User: Damintsev Andrey
 * Date: 06.10.13
 * Time: 16:54
 */
public abstract class Task {

    public abstract String getCommand();
    public abstract Connection getConnection();
}
