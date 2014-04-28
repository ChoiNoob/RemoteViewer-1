package com.damintsev.client.v3.pages.frames;

import com.damintsev.client.v3.utilities.Alarm;
import com.damintsev.client.v3.utilities.UIButton;
import com.damintsev.common.history.Link;
import com.damintsev.common.utils.Dialogs;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.sencha.gxt.core.client.util.Padding;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.ToggleButton;
import com.sencha.gxt.widget.core.client.button.ToolButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

/**
 * User: adamintsev
 * Date: 20.12.13
 * Top bar with buttons
 */
public class StatusBar {

    private ToolBar toolBar;

    public StatusBar() {
        toolBar = new ToolBar();

        toolBar.setPadding(new Padding(2, 10, 2, 10));
        toolBar.setPack(BoxLayoutContainer.BoxLayoutPack.END);
        toolBar.setHorizontalSpacing(20);

        addTurnAlarmButton();
        addEditButton();
        logOutButton();
    }

    public ToolBar getToolBar() {
        return toolBar;
    }

    private void addTurnAlarmButton() {
        final ToggleButton muteButton = UIButton.createToggleToolButton("web/icons/music.png");
        muteButton.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                if(muteButton.getValue()) {
                    Alarm.getInstance().alarmOn();
                } else {
                    Alarm.getInstance().alarmOff();
                }
            }
        });
        muteButton.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
            @Override
            public void onValueChange(ValueChangeEvent<Boolean> event) {
                System.out.println("changed=" + event.getValue());
            }
        });
        toolBar.add(muteButton);
    }

    private void addEditButton() {
        ToolButton editButton = new ToolButton(ToolButton.GEAR);
        editButton.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                History.newItem(Link.EDIT.toString());
            }
        });

        toolBar.add(editButton);
    }

    private void logOutButton() {
        ToolButton editButton = new ToolButton(ToolButton.CLOSE);
        editButton.setToolTip("Выйти");
        editButton.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                Dialogs.confirm("Вы уверены, что хотите выйти?", new Runnable() {
                    @Override
                    public void run() {
//                        com.google.gwt.user.client.Window.open("api/logout", "", "");
                        com.google.gwt.user.client.Window.Location.assign("api/logout");
                    }
                });
            }
        });

        toolBar.add(editButton);
    }
}
