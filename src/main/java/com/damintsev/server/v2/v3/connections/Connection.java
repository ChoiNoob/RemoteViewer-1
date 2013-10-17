package com.damintsev.server.v2.v3.connections;

import com.damintsev.client.v3.items.Station;
import com.damintsev.client.v3.items.task.Task;
import com.damintsev.client.v3.items.task.executors.TaskProcessor;
import com.damintsev.client.v3.items.task.TaskState;
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

}
