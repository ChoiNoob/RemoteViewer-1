package com.damintsev.client.v3.uiitems;

import com.damintsev.client.old.devices.Item;
import com.damintsev.common.beans.Station;
import com.damintsev.common.beans.TaskState;
import com.damintsev.common.utils.Position;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

/**
 * User: Damintsev Andrey
 * Date: 03.08.13
 * Time: 2:09
 */
public abstract class UIItem extends Widget implements IsWidget {

    private Widget widget;
    protected Item item;
    protected TaskState taskState;
    protected Image image;

    protected abstract Widget widget();
    protected abstract int getLeft();
    protected abstract int getTop();
    protected abstract int getWidth();
    protected abstract int getHeight();
    public abstract void openEditor(Runnable runnable);

    @Override
    public Widget asWidget() {
        System.out.println("asdWWWW");
        if (widget == null) widget = widget();
        return widget;
    }

    public UIItem(Item item) {
        super();
        this.item = item;
        taskState = new TaskState();
        setElement(widget().getElement());
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

    //    public boolean haveChildrens() {
//        return getId().startsWith("s");
//    }
//
//    public boolean isChild(UIItem child) {
//        return child.getId().startsWith("s");
//    }

    public String getParentId() {
        return item.getParentId();
    }

    public TaskState getTaskState() {
        return taskState;
    }

    public void setTaskState(TaskState status) {
        this.taskState = status;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }
}
