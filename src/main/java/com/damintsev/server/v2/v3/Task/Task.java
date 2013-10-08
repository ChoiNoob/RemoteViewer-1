package com.damintsev.server.v2.v3.task;

import com.damintsev.client.devices.Response;
import com.damintsev.client.devices.Station;
import com.damintsev.server.v2.machine.state.TaskState;

/**
 * User: Damintsev Andrey
 * Date: 06.10.13
 * Time: 16:54
 */
public abstract class Task {

    private TaskType type;
    private Long id;

    public abstract String getCommand();
    public abstract Station getStation();
    public abstract TaskState process(Response execute);

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
