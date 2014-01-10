package com.damintsev.client.v3.pages.frames;

import com.damintsev.client.EventBus;
import com.damintsev.common.event.StartEditEvent;
import com.google.gwt.user.client.ui.Image;
import com.sencha.gxt.core.client.util.Padding;
import com.sencha.gxt.widget.core.client.button.ToolButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

/**
 * User: adamintsev
 * Date: 20.12.13
 * //todo написать комментарии
 */
public class StatusBar {

    private ToolBar toolBar;

    public StatusBar() {
        toolBar = new ToolBar();

        toolBar.setPadding(new Padding(2, 10, 2, 10));
        toolBar.setPack(BoxLayoutContainer.BoxLayoutPack.END);
        toolBar.setHorizontalSpacing(20);
        FieldLabel label = new FieldLabel(new Image("web/icons/run.png"), "Состояние");
        label.setLabelWidth(70);
        label.getElement().getStyle().setBackgroundColor("#e8e8e8");
//        toolBar.getElement().appendChild(label.getElement());

        ToolButton editButton = new ToolButton(ToolButton.GEAR);
        editButton.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                EventBus.get().fireEvent(new StartEditEvent());
            }
        });
        toolBar.add(editButton);
    }

    public ToolBar getToolBar() {
        return toolBar;
    }
}
