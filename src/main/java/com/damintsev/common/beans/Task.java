package com.damintsev.common.beans;

import com.damintsev.client.old.devices.Item;
import com.damintsev.common.visitor.Visitor;

/**
 * User: Damintsev Andrey
 * Date: 09.10.13
 * Time: 0:43
 */
public class Task extends Item {

    private Long id;
    private String name;
    private String command;
    private TaskType type;
    private Station station;
    private Long imageId;

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

    @Override
    public String getParentId() {
        return getStation().getStringId();
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

    public String getStringId() {
        return station.getStringId() + ":t" + getId();
    }

    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public Long getImage() {
        if(imageId == null || imageId == 0) imageId = DefaultImages.TASK.getValue();
        return imageId;
    }

    public void setImage(Long imageId) {
        this.imageId = imageId;
    }
}
