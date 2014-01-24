package com.damintsev.client.v3.uiitems;

import com.damintsev.client.old.devices.Item;
import com.damintsev.client.v3.pages.windows.AddTaskWindow;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * User: Damintsev Andrey
 * Date: 09.10.13
 * Time: 22:34
 */
public class UITask extends UIItem implements HasConnections {

    private Label label;
    private Image image;

    public UITask(Item item) {
        super(item);
    }

    @Override
    public Widget widget() {
        image = new Image("image?imageId=" + item.getImage());
        label = new Label();
        label.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        label.getElement().appendChild(image.getElement());
        Label nameLabel = new Label(getName());
        if(getName() != null)
            label.getElement().appendChild(nameLabel.getElement());
        nameLabel.setStyleName("tooltip");
        return label;
    }

    @Override
    public void openEditor(Runnable runnable) {
        AddTaskWindow.get().show(item.getId(), runnable);
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
}
