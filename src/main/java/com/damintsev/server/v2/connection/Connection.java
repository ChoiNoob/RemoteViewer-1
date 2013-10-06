package com.damintsev.server.v2.connection;

import com.damintsev.client.devices.Response;

import java.util.Properties;

/**
 * User: Damintsev Andrey
 * Date: 06.10.13
 * Time: 16:19
 */
public abstract class Connection {

    public abstract void create(Properties properties);
    public abstract Response process(String command);
    public abstract void destroy();
    public abstract Long getId();
}
