package com.damintsev.client.v3.uiitems;

import com.damintsev.client.devices.Item;
import com.damintsev.client.v3.items.Station;
import com.damintsev.client.devices.UIItem;
import com.damintsev.client.v3.pages.windows.AddStationWindow;
import com.damintsev.client.v3.pages.windows.AddTaskWindow;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.IconHelper;

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
        AddStationWindow.get().show(item.getId(), runnable);
    }

    @Override
    protected String initImage() {
        return "station";
    }
}
