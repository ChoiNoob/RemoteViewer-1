package com.damintsev.client.uiframe;

import com.google.gwt.dom.client.Style;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
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
 * Date: 03.08.13
 * Time: 0:49
 */
public class UISettingsPanel {

    private static UISettingsPanel instance;

    public static UISettingsPanel get() {
        if(instance == null) instance = new UISettingsPanel();
        return instance;
    }

    private UISettingsPanel(){

    }

    public Widget getContent() {
        final ContentPanel panel = new ContentPanel();
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
        panel.collapse();

        new Draggable(panel);
        panel.addButton(new TextButton("Asd"));
        panel.addButton(new TextButton("ddd"));
        panel.setButtonAlign(BoxLayoutContainer.BoxLayoutPack.END);

        ButtonBar bar = new ButtonBar();
//        bar.getElement().getStyle().set;
        bar.add(new TextButton("Save", new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent event) {
                AddDeviceWindow.get().show();
            }
        }));
        bar.add(new TextButton("Cancel"));
        bar.setHBoxLayoutAlign(HBoxLayoutContainer.HBoxLayoutAlign.BOTTOM);
//        panel.add(bar, new MarginData(0,-1,0,0));
        panel.add(bar, new VerticalLayoutContainer.VerticalLayoutData(1,-1));


        return panel;
    }
}
