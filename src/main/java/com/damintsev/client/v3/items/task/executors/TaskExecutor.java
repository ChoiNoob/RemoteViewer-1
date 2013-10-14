package com.damintsev.client.v3.items.task.executors;

import com.damintsev.client.v3.items.task.Task;
import com.damintsev.client.v3.items.task.TaskState;
import com.damintsev.server.v2.v3.exceptions.ExecutingTaskException;

/**
 * User: Damintsev Andrey
 * Date: 06.10.13
 * Time: 16:54
 */
public abstract class TaskExecutor {

    protected abstract TaskState process(String command) throws ExecutingTaskException;

    public static TaskState process(Task task, String result) {
        switch (task.getType()) {
            case TELNET:
                return new StatusChannelTask().process(result);
            case IP:
                return new PingTask().process(result);
        }
        return null;
    }
}
