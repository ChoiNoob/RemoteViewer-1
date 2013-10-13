package com.damintsev.client.uiframe;

import com.damintsev.client.v3.pages.frames.MonitoringFrame;
import com.damintsev.client.v3.pages.frames.SettingsFrame;
import com.google.gwt.user.client.ui.*;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import com.sencha.gxt.widget.core.client.container.Viewport;

/**
 * User: Damintsev Andrey
 * Date: 01.08.13
 * Time: 23:17
 */
public class UIRootPanel {

    private static UIRootPanel instance;

    public static UIRootPanel get() {
        if(instance == null) instance = new UIRootPanel();
        return instance;
    }

    private UIRootPanel(){
    }

    public Widget getContent() {
        Viewport viewport = new Viewport();
        viewport.setStyleName("gwt_main");

        BorderLayoutContainer body = new BorderLayoutContainer();
        viewport.add(body);

        FlowLayoutContainer footer = new FlowLayoutContainer();
        footer.setHeight(20);
        footer.setStyleName("footer");
        body.setSouthWidget(footer, new BorderLayoutContainer.BorderLayoutData(20));

        body.setCenterWidget(MonitoringFrame.get().getContent());
        final ContentPanel settings = (ContentPanel) SettingsFrame.get().getContent();
        ((AbsolutePanel)body.getCenterWidget()).add(settings);


        ((AbsolutePanel)body.getCenterWidget()).add(UIBillingPanel.getInstance().getContent());

//        ((AbsolutePanel)body.getCenterWidget()).add(UIBillingPanel.getInstance().getContent());
        return viewport;
    }
}
