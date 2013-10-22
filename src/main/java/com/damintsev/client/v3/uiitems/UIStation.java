package com.damintsev.client.v3.uiitems;

import com.damintsev.client.old.devices.Item;
import com.damintsev.client.old.devices.UIItem;
import com.damintsev.client.v3.pages.windows.AddStationWindow;
import com.google.gwt.user.client.ui.Widget;

/**
 * User: Damintsev Andrey
 * Date: 10.10.13
 * Time: 0:10
 */
public class UIStation extends UIItem {

    public UIStation(Item item) {
        super(item);
    }

    @Override
    public Widget asWidget() {
        return this;
    }

    @Override
    public void openEditor(Runnable runnable) {
        AddStationWindow.getInstance().show(item.getId(), runnable);
    }

    @Override
    protected String initImage() {
        return "station";
    }
}
