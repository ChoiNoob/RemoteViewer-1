package com.damintsev.client.v3.items.task.executors;

import com.damintsev.client.v3.items.task.Task;
import com.damintsev.client.v3.items.task.TaskState;

/**
 * User: Damintsev Andrey
 * Date: 06.10.13
 * Time: 16:54
 */
public abstract class TaskExecutor {

    protected abstract TaskState process(String command);

    public static TaskState process(Task task, String result) {
        switch (task.getType()) {
            case TELNET:
                return new StatusChannelTask().process(result);
            case PING:
                return new PingTask().process(result);
        }
        return null;
    }
}
