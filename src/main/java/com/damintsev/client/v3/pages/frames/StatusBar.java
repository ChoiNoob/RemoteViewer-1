package com.damintsev.client.v3.pages.frames;

import com.damintsev.client.EventBus;
import com.damintsev.common.event.StartEditEvent;
import com.damintsev.common.utils.Dialogs;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.widget.client.TextButton;
import com.sencha.gxt.core.client.util.Padding;
import com.sencha.gxt.widget.core.client.button.ToolButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
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
