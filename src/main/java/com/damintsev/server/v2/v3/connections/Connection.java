package com.damintsev.server.v2.v3.connections;

import com.damintsev.common.beans.Station;
import com.damintsev.common.beans.Task;
import com.damintsev.server.v2.v3.exceptions.ConnectException;
import com.damintsev.server.v2.v3.exceptions.ExecutingTaskException;

/**
 * User: Damintsev Andrey
 * Date: 06.10.13
 * Time: 16:19
 */
public abstract class Connection {

    public abstract Connection init(Station station) throws ConnectException;

    public abstract String execute(Task task) throws ExecutingTaskException;

    public abstract void destroy();

    public abstract boolean isConnected();
}
