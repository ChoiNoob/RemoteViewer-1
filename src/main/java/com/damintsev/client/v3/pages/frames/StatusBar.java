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
        toolBar.getElement().appendChild(label.getElement());

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

/*
    element.style {
        background-color: rgb(232, 232, 232);
        margin: 5px;
        left: 1100px;
        top: 2px;
        width: 110px;
        align-content: center;
        padding-left: 15px;
        border-radius: 10px;
        -moz-border-radius: 10px;
        -webkit-border-radius: 10px;
    }

    .GGDYDFWDCU .GGDYDFWDEU {
        clear: left;
        display: block;
        float: left;
        padding: 1px 0 0;
        position: relative;
        z-index: 2;
        -moz-user-select: none;
}


    */
}
