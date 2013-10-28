package com.damintsev.client.v3.uiitems;

import com.damintsev.client.old.devices.Item;
import com.damintsev.client.v3.pages.windows.AddStationWindow;
import com.damintsev.common.beans.TaskState;
import com.damintsev.common.utils.Position;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * User: Damintsev Andrey
 * Date: 10.10.13
 * Time: 0:10
 */
public class UIStation extends UIItem {

    private Label label;
    private Label nameLabel;

    public UIStation(Item item) {
        super(item);
    }

    @Override
    protected int getLeft() {
        return label.getAbsoluteLeft();
    }

    @Override
    protected int getTop() {
        return label.getAbsoluteTop();
    }

    @Override
    protected int getWidth() {
        return image.getWidth();
    }

    @Override
    protected int getHeight() {
        return image.getHeight();
    }

    @Override
    public Widget widget() {
        image = new Image("image?type=station");
        label = new Label();
        label.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        label.getElement().appendChild(image.getElement());
        nameLabel = new Label(getName());
        if(getName() != null) {
            label.getElement().appendChild(nameLabel.getElement());
        }
        nameLabel.setStyleName("tooltip");
        return label;
    }

    @Override
    public void openEditor(Runnable runnable) {
        AddStationWindow.getInstance().show(item.getId(), runnable);
    }

    @Override
    public void setTaskState(TaskState status) {
        System.out.println("State station=" + status.getState());
        nameLabel.getElement().getStyle().setBackgroundColor(status.getState().getColor());
        super.setTaskState(status);
    }
}
