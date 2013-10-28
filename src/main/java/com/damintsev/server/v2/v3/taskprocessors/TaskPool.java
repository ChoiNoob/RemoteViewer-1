package com.damintsev.server.v2.v3.taskprocessors;

import com.damintsev.common.beans.TaskType;

import java.util.HashMap;
import java.util.Map;

/**
 * User: adamintsev Date: 17.10.13 Time: 17:25
 */
public class TaskPool {

    private static TaskPool instance;

    public static TaskPool getInstance() {
        if (instance == null) instance = new TaskPool();
        return instance;
    }

    private Map<TaskType, TaskProcessor> pool;

    private TaskPool() {
        pool = new HashMap<TaskType, TaskProcessor>();
        pool.put(TaskType.IP, new IPTask());
        pool.put(TaskType.ISDN, new StatusChannelTask());
    }

    public TaskProcessor getTaskProcessor(TaskType type) {
        return pool.get(type);
    }
}
