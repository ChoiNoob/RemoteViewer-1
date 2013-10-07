package com.damintsev.server.v2.connection;

import com.damintsev.client.devices.Response;
import com.damintsev.client.devices.Station;
import com.damintsev.server.v2.Task.Task;

import java.util.Properties;

/**
 * User: Damintsev Andrey
 * Date: 06.10.13
 * Time: 16:19
 */
public abstract class Connection {

    protected abstract Connection init(Station station);
    public abstract Response process(Task task);
    public abstract void destroy();
    public abstract Long getId();
}
