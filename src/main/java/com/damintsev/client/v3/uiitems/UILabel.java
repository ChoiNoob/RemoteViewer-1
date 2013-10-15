package com.damintsev.client.v3.uiitems;

import com.damintsev.client.devices.Item;
import com.damintsev.client.devices.UIItem;
import com.damintsev.client.v3.pages.windows.AddStationWindow;
import com.damintsev.client.v3.pages.windows.LabelWindow;
import com.google.gwt.user.client.ui.Widget;

/**
 * User: Damintsev Andrey
 * Date: 15.10.13
 * Time: 21:59
 */
public class UILabel extends UIItem {

    public UILabel(Item item) {
        super(item);
    }

    @Override
    public Widget asWidget() {
        return this;
    }

    @Override
    public void openEditor(Runnable runnable) {
        LabelWindow.get().show(item.getId(), runnable);
    }
}
