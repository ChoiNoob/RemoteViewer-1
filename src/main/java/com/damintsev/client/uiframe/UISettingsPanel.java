package com.damintsev.client.uiframe;

import com.damintsev.client.devices.CommonDevice;
import com.damintsev.client.devices.Station;
import com.damintsev.client.devices.UIItem;
import com.damintsev.utils.Dialogs;
import com.damintsev.utils.Utils;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.ButtonCell;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
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

    private ContentPanel panel;

    public Widget getContent() {
        panel = new ContentPanel();
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
        panel.setPixelSize(170, 500);
        panel.getElement().getStyle().setTop(5, Style.Unit.PX);
        panel.getElement().getStyle().setRight(5, Style.Unit.PX);
        panel.getElement().getStyle().setPosition(Style.Position.ABSOLUTE);
        panel.collapse();

        VBoxLayoutContainer buttons = new VBoxLayoutContainer();
        buttons.setVBoxLayoutAlign(VBoxLayoutContainer.VBoxLayoutAlign.CENTER);

        TextButton station = new TextButton("Добавить станцию", new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent event) {
                AddStationWindow.get().show(null);
            }
        });
        station.setIconAlign(ButtonCell.IconAlign.BOTTOM);
        station.setIcon(Utils.getImage("hipath"));
        buttons.add(station, new BoxLayoutContainer.BoxLayoutData(new Margins(5)));

        TextButton device = new TextButton("Добавить устройство",new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent event) {
                AddDeviceWindow.get().show(null);
            }
        });
        device.setIcon(Utils.getImage("cloud"));
        device.setIconAlign(ButtonCell.IconAlign.BOTTOM);
        buttons.add(device);

        TextButton edit = new TextButton("редактировать", new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent event) {
                UIItem selected = (UIItem) UICenterField.get().getSelected();
                if (selected == null) Dialogs.alert("Выберите устройство");
                else {
                    switch (selected.getType()) {
                        case STATION:
                            AddStationWindow.get().show((Station) selected.getItem().getData());
                            break;
                        case IP:
                        case ISDN:
                            AddDeviceWindow.get().show((CommonDevice) selected.getItem().getData());
                            break;
                    }
                }
            }
        });
        buttons.add(edit);
        panel.add(buttons);

        TextButton save = new TextButton("Сохранить", new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent event) {
                UICenterField.get().saveItemPositions();
                collapse();
            }
        });
        panel.addButton(save);
        TextButton cancel = new TextButton("Отмена", new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent event) {
                UICenterField.get().revertItemPositions();
                collapse();
            }
        });

        panel.addButton(cancel);
        panel.setButtonAlign(BoxLayoutContainer.BoxLayoutPack.CENTER);

        return panel;
    }

    public void expand() {
        panel.expand();
    }

    public void collapse() {
        panel.collapse();
    }

    public boolean isExpanded() {
        return panel.isExpanded();
    }
}
