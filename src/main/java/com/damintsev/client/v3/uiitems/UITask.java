package com.damintsev.client.v3.uiitems;

import com.damintsev.client.devices.Item;
import com.damintsev.client.devices.UIItem;
import com.damintsev.client.v3.items.task.Task;
import com.damintsev.client.v3.pages.windows.AddTaskWindow;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * User: Damintsev Andrey
 * Date: 09.10.13
 * Time: 22:34
 */
public class UITask extends UIItem {

    public UITask(Item item) {
        super(item);
    }

    @Override
    public Widget asWidget() {
        setHorizontalAlignment(ALIGN_CENTER);
        if(getImage() != null) {
            image = new Image(getImage());
            getElement().appendChild(image.getElement());
        }
        Label label = new Label(getName());
        if(getName() != null)
            getElement().appendChild(label.getElement());
        label.setStyleName("tooltip");
        return this;
    }

    @Override
    public void openEditor(Runnable runnable) {
        AddTaskWindow.get().show(item.getId(), runnable);
    }
}
