package com.damintsev.client.v3.items;

import com.damintsev.client.devices.Item;
import com.damintsev.client.v3.items.visitor.Visitor;
import com.damintsev.client.v3.items.task.TaskType;

/**
 * User: Damintsev Andrey
 * Date: 14.10.13
 * Time: 23:18
 */
public class Label extends Item {

    private String name;
    private Long id;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public TaskType getType() {
        return TaskType.LABEL;

    }

    @Override
    public String getStringId() {
        return "l" + getId();
    }

    @Override
    public Station getStation() {
        return null;
    }

    @Override
    public String getParentId() {
        return null;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
