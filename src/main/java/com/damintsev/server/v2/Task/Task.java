package com.damintsev.server.v2.Task;

import com.damintsev.client.devices.Response;
import com.damintsev.client.devices.Station;
import com.damintsev.server.v2.connection.Connection;

/**
 * User: Damintsev Andrey
 * Date: 06.10.13
 * Time: 16:54
 */
public abstract class Task {

    private TaskType type;
    private String id;

    public abstract String getCommand();
    public abstract Station getStation();

    public abstract Response process(Response execute);

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}