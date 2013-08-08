package com.damintsev.client.uiframe;

import com.damintsev.client.Utils;
import com.damintsev.utils.Dialogs;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.i18n.client.HasDirection;
import com.google.gwt.layout.client.Layout;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.sencha.gxt.core.client.util.IconHelper;
import com.sencha.gxt.fx.client.Draggable;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.*;
import com.sencha.gxt.widget.core.client.event.BeforeExpandEvent;
import com.sencha.gxt.widget.core.client.event.CollapseEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;

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
        Label text = new Label("Avanti-Telecommunications 2013");
        text.getElement().getStyle().setFontSize(15, Style.Unit.PX);
        text.getElement().getStyle().setColor("gray");
        text.setHorizontalAlignment(HasHorizontalAlignment.HorizontalAlignmentConstant.endOf(HasDirection.Direction.DEFAULT));
        footer.add(text);
        footer.setHeight(20);
        footer.setStyleName("footer");
        body.setSouthWidget(footer, new BorderLayoutContainer.BorderLayoutData(20));

        Image logo = new Image(Utils.getImage("logo"));
        logo.setPixelSize(64, 64);
        logo.setStyleName("gwt_logo");

        body.setCenterWidget(UICenterField.get().getContent());
        ((AbsolutePanel)body.getCenterWidget()).add(logo);
        final ContentPanel settings = (ContentPanel) UISettingsPanel.get().getContent();
        ((AbsolutePanel)body.getCenterWidget()).add(settings);

        return viewport;
    }
}
