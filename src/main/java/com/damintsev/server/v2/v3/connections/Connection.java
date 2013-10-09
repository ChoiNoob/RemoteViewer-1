package com.damintsev.server.v2.v3.connections;

import com.damintsev.client.devices.Station;
import com.damintsev.client.v3.items.task.Task;
import com.damintsev.client.v3.items.task.executors.TaskExecutor;
import com.damintsev.client.v3.items.task.TaskState;
import com.damintsev.server.v2.v3.exceptions.ConnectException;
import com.damintsev.server.v2.v3.exceptions.ExecutingTaskException;

/**
 * User: Damintsev Andrey
 * Date: 06.10.13
 * Time: 16:19
 */
public abstract class Connection {

    protected abstract Connection init(Station station) throws ConnectException;
    protected abstract String process(Task task) throws ExecutingTaskException;
    public abstract void destroy();
    public abstract Long getId();

    public TaskState execute(Task task) throws ExecutingTaskException {
        return TaskExecutor.process(task, process(task));
    }
}
