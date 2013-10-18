package com.damintsev.client.v3.uiitems;

import com.damintsev.client.old.devices.Item;
import com.damintsev.client.old.devices.UIItem;
import com.damintsev.client.v3.pages.windows.AddStationWindow;
import com.damintsev.client.v3.pages.windows.LabelWindow;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * User: Damintsev Andrey
 * Date: 15.10.13
 * Time: 21:59
 */
public class UILabel extends UIItem {

    public UILabel(Item item) {
        super(item);
        setWidth("auto");
    }

    @Override
    protected String initImage() {
        return null;
    }

    @Override
    public Widget getParent() {
        System.out.println("FUCKCCKCKCK");
        return super.getParent();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public Widget asWidget() {     //todo этот метод не вызвается! Надо придумать как его ызнвтаь!
        Label label = new Label(getName(), true);
//        label.setAutoHorizontalAlignment(ALIGN_JUSTIFY);
//        label.setSize("200px");
        label.getElement().getStyle().setProperty("width", "auto");
//        super.setWidth("auto");
        return this;
    }

    @Override
    public void openEditor(Runnable runnable) {
        LabelWindow.get().show(item.getId(), runnable);
    }
}
