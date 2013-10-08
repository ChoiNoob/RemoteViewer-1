package com.damintsev.server.v2.v3.task;

import com.damintsev.client.devices.Station;

/**
 * User: Damintsev Andrey
 * Date: 09.10.13
 * Time: 0:43
 */
public class Task {

    private Long id;
    private String name;
    private String command;
    private TaskType type;
    private Station station;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
    }
}
