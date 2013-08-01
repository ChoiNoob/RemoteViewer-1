package com.damintsev.client.uiframe;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.i18n.client.HasDirection;
import com.google.gwt.layout.client.Layout;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.user.client.ui.*;
import com.sencha.gxt.core.client.util.IconHelper;
import com.sencha.gxt.fx.client.Draggable;
import com.sencha.gxt.widget.core.client.ContentPanel;
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

        Image logo = new Image(IconHelper.getImageResource(UriUtils.fromString("/web/img/avanti_logo_64.png"), 64, 64)); //todo image resources
        logo.setPixelSize(64, 64);
        logo.setStyleName("gwt_logo");

        body.setCenterWidget(UICenterField.get().getContent());
        ((AbsolutePanel)body.getCenterWidget()).add(logo);



        final ContentPanel panel = new ContentPanel();
        ((AbsolutePanel)body.getCenterWidget()).add(new TextButton("asdas", new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent event) {
                if (panel.isVisible())
                    panel.collapse();
                else panel.expand();
            }
        }));
        panel.addBeforeExpandHandler(new BeforeExpandEvent.BeforeExpandHandler() {
            public void onBeforeExpand(BeforeExpandEvent event) {
                panel.show();
            }
        });
        panel.addCollapseHandler(new CollapseEvent.CollapseHandler() {
            public void onCollapse(CollapseEvent event) {
                panel.hide();
            }
        });
        panel.setAnimationDuration(1000);
        panel.setAnimCollapse(true);
        panel.setHeadingText("Режим редактирования");
        panel.setCollapsible(true);
        panel.setPixelSize(160, 500);
        panel.getElement().getStyle().setTop(10, Style.Unit.PX);
        panel.getElement().getStyle().setRight(10, Style.Unit.PX);
        panel.getElement().getStyle().setPosition(Style.Position.ABSOLUTE);
        new Draggable(panel);
        panel.addButton(new TextButton("Save"));
        panel.addButton(new TextButton("Cancel"));
        ((AbsolutePanel)body.getCenterWidget()).add(panel);

//        body.setEastWidget(panel, settingsLayout);
        PortalLayoutContainer portal = new PortalLayoutContainer(1);
//        portal.add(panel);
//        body.add(portal);

        return viewport;
    }
}
