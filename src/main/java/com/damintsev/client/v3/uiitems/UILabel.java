package com.damintsev.client.v3.uiitems;

import com.damintsev.client.old.devices.Item;
import com.damintsev.client.v3.pages.windows.LabelWindow;
import com.damintsev.common.utils.Position;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * User: Damintsev Andrey
 * Date: 15.10.13
 * Time: 21:59
 */
public class UILabel extends UIItem {

    private Label label;

    public UILabel(Item item) {
        super(item);
        label = new Label(getName(), true);
        label.setAutoHorizontalAlignment(HasHorizontalAlignment.ALIGN_JUSTIFY);
        label.getElement().getStyle().setProperty("width", "auto");
        label.setStyleName("tooltip");
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
    public Position getCenterPosition() {
        return null;
    }

    @Override
    protected int getWidth() {
        return 0;
    }

    @Override
    protected int getHeight() {
        return 0;
    }

    @Override
    public Widget widget() {
        return label;
    }

    @Override
    public void openEditor(Runnable runnable) {
        LabelWindow.get().show(item.getId(), runnable);
    }
}
