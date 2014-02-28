package com.damintsev.client.v3.uiitems;

import com.damintsev.client.old.devices.Item;
import com.damintsev.common.uientity.Station;
import com.damintsev.common.uientity.TaskState;
import com.damintsev.common.utils.Position;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * User: Damintsev Andrey
 * Date: 03.08.13
 * Time: 2:09
 */
public abstract class UIItem extends Label {

    protected Item item;
    protected TaskState taskState;

    protected abstract Widget widget();
    protected abstract int getLeft();
    protected abstract int getTop();
    protected abstract int getWidth();
    protected abstract int getHeight();
    public abstract void openEditor(Runnable runnable);

    public UIItem(Item item) {
        this.item = item;
        taskState = new TaskState();

    //todo    tooltip = StatusToolTip.getInstance();
    //todo     ToolTip tt = new ToolTip(this, tooltip);
    }

    //todo StatusToolTip tooltip;

    protected void initWidget(Widget widget) {
        getElement().appendChild(widget.getElement());
    }

    public void savePosition() {
        item.getPosition().x = getLeft();
        item.getPosition().y = getTop();
    }

    public Position getCenterPosition() {
        int x = getLeft() + getWidth() / 2;
        int y = getTop() + getHeight() / 2;
        return new Position(x, y);
    }

    public Position getPosition() {
        return item.getPosition();
    }

    protected String getName() {
        return item.getName();
    }

    public String getId() {
        return item.getStringId();
    }

    public Station getStation() {
        return item.getStation();
    }

    public String getParentId() {
        return item.getParentId();
    }

    public TaskState getTaskState() {
        return taskState;
    }

    public void setTaskState(TaskState status) {
//todo        tooltip.setMessage(status);
        this.taskState = status;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }
}
