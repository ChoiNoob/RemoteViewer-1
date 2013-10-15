package com.damintsev.client.v3.pages.frames;

import com.damintsev.client.devices.Item;
import com.damintsev.client.devices.UIItem;
import com.damintsev.client.service.Service;
import com.damintsev.client.v3.items.Label;
import com.damintsev.client.v3.items.Station;
import com.damintsev.client.v3.items.task.Task;
import com.damintsev.client.v3.pages.windows.AddTaskWindow;
import com.damintsev.client.v3.pages.windows.AddStationWindow;
import com.damintsev.client.v3.pages.windows.LabelWindow;
import com.damintsev.client.v3.uiitems.FileUploadExample;
import com.damintsev.client.windows.PrefixConfigWindow;
import com.damintsev.utils.Dialogs;
import com.damintsev.utils.Utils;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.ButtonCell;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.event.BeforeExpandEvent;
import com.sencha.gxt.widget.core.client.event.CollapseEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;

/**
 * User: Damintsev Andrey
 * Date: 03.08.13
 * Time: 0:49
 */
public class SettingsFrame {

    private static SettingsFrame instance;

    public static SettingsFrame get() {
        if(instance == null) instance = new SettingsFrame();
        return instance;
    }

    private SettingsFrame(){
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
                AddStationWindow.get().show(null, null);
            }
        });
        station.setIconAlign(ButtonCell.IconAlign.BOTTOM);
        station.setIcon(Utils.getImage("hipath"));
        buttons.add(station, new BoxLayoutContainer.BoxLayoutData(new Margins(5)));

        TextButton device = new TextButton("Добавить устройство",new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent event) {
                AddTaskWindow.get().show(null, null);
            }
        });
        device.setIcon(Utils.getImage("cloud"));
        device.setIconAlign(ButtonCell.IconAlign.BOTTOM);
        buttons.add(device, new BoxLayoutContainer.BoxLayoutData(new Margins(5)));

        TextButton label = new TextButton("Добавить комментарий",new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent event) {
                LabelWindow.get().show(null, null);
            }
        });
        buttons.add(label, new BoxLayoutContainer.BoxLayoutData(new Margins(5)));

        TextButton edit = new TextButton("редактировать", new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent event) {
                final UIItem selected = (UIItem) MonitoringFrame.get().getSelected();
                if (selected == null) Dialogs.alert("Выберите устройство");
                else {
                    selected.openEditor(new Runnable() {
                        public void run() {
                            //todo ?!?!?!
                        }
                    });
                }
            }
        });
        buttons.add(edit, new BoxLayoutContainer.BoxLayoutData(new Margins(5)));

        buttons.add(new TextButton("Загрузка изображений", new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent event) {
                FileUploadExample.getInstance().show();
            }
        }),new BoxLayoutContainer.BoxLayoutData(new Margins(5)));

        buttons.add(new TextButton("Настройка префиксов", new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent event) {
                PrefixConfigWindow.get().show();
            }
        }),new BoxLayoutContainer.BoxLayoutData(new Margins(5)));
        panel.add(buttons);

        TextButton save = new TextButton("Сохранить", new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent event) {
                MonitoringFrame.get().stopEditing();
                collapse();
//                reload();
            }
        });
        panel.addButton(save);
        TextButton cancel = new TextButton("Отмена", new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent event) {
                MonitoringFrame.get().stopEditing();
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
