package com.damintsev.server.v2.v3.task.executors;

import com.damintsev.client.devices.Response;
import com.damintsev.client.devices.Station;
import com.damintsev.server.v2.v3.task.Task;
import com.damintsev.server.v2.v3.task.TaskState;
import com.damintsev.server.v2.v3.task.TaskType;

/**
 * User: Damintsev Andrey
 * Date: 06.10.13
 * Time: 16:54
 */
public abstract class TaskExecutor {

//    public abstract Station getStation();
    protected abstract TaskState process(String command);

    public static TaskState process(Task task, String result) {
        switch (task.getType()) {
            case TELNET:
                return new StatusChannelTask().process(result);
        }
        return null;
    }
}
