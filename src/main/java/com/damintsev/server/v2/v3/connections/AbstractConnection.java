package com.damintsev.server.v2.v3.connections;

import com.damintsev.common.uientity.Station;
import com.damintsev.common.uientity.Task;
import com.damintsev.server.v2.v3.exceptions.ConnectionException;
import com.damintsev.server.v2.v3.exceptions.ExecutingTaskException;

/**
 * User: Damintsev Andrey
 * Date: 06.10.13
 * Time: 16:19
 */
public abstract class AbstractConnection implements Connection {

    public abstract Connection init(Station station) throws ConnectionException;

    public abstract String execute(Task task) throws ExecutingTaskException, ConnectionException;

    public abstract void destroy();

    public abstract boolean isConnected();
}
