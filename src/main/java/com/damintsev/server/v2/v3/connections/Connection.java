package com.damintsev.server.v2.v3.connections;

import com.damintsev.common.uientity.Station;
import com.damintsev.common.uientity.Task;
import com.damintsev.server.v2.v3.exceptions.ConnectionException;
import com.damintsev.server.v2.v3.exceptions.ExecutingTaskException;

/**
 * @author Damintsev Andrey
 *         29.01.14.
 */
public interface Connection {

    Connection init(Station station) throws ConnectionException;

    String execute(Task task) throws ExecutingTaskException, ConnectionException;

    void destroy();

    boolean isConnected();
}
