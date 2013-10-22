package com.damintsev.client.v3.pages.frames;

import com.damintsev.client.old.devices.UIItem;
import com.damintsev.client.v3.pages.windows.AddTaskWindow;
import com.damintsev.client.v3.pages.windows.AddStationWindow;
import com.damintsev.client.v3.pages.windows.LabelWindow;
import com.damintsev.client.v3.pages.windows.FileUploadWindow;
import com.damintsev.client.old.devices.windows.PrefixConfigWindow;
import com.damintsev.common.utils.Dialogs;
import com.damintsev.common.utils.Utils;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.dom.client.Style;
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
                GWT.runAsync(new RunAsyncCallback() {
                    @Override
                    public void onFailure(Throwable reason) {
                        //To change body of implemented methods use File | Settings | File Templates.
                    }

                    @Override
                    public void onSuccess() {
                        AddStationWindow.getInstance().show(null, null);
                    }
                });
            }
        });
        station.setIconAlign(ButtonCell.IconAlign.BOTTOM);
        station.setIcon(Utils.getImage("hipath"));
        buttons.add(station, new BoxLayoutContainer.BoxLayoutData(new Margins(5)));

        TextButton device = new TextButton("Добавить устройство",new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent event) {
                GWT.runAsync(new RunAsyncCallback() {
                    @Override
                    public void onFailure(Throwable reason) {
                        //To change body of implemented methods use File | Settings | File Templates.
                    }

                    @Override
                    public void onSuccess() {
                        AddTaskWindow.get().show(null, null);
                    }
                });

            }
        });
        device.setIcon(Utils.getImage("cloud"));
        device.setIconAlign(ButtonCell.IconAlign.BOTTOM);
        buttons.add(device, new BoxLayoutContainer.BoxLayoutData(new Margins(5)));

        TextButton label = new TextButton("Добавить комментарий",new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent event) {
                GWT.runAsync(new RunAsyncCallback() {
                    @Override
                    public void onFailure(Throwable reason) {
                        //To change body of implemented methods use File | Settings | File Templates.
                    }

                    @Override
                    public void onSuccess() {
                        LabelWindow.get().show(null, null);
                    }
                });
            }
        });
        buttons.add(label, new BoxLayoutContainer.BoxLayoutData(new Margins(5)));

        TextButton edit = new TextButton("редактировать", new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent event) {
                GWT.runAsync(new RunAsyncCallback() {
                    @Override
                    public void onFailure(Throwable reason) {
                        //To change body of implemented methods use File | Settings | File Templates.
                    }

                    @Override
                    public void onSuccess() {
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

            }
        });
        buttons.add(edit, new BoxLayoutContainer.BoxLayoutData(new Margins(5)));

        buttons.add(new TextButton("Загрузка изображений", new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent event) {
                GWT.runAsync(new RunAsyncCallback() {
                    @Override
                    public void onFailure(Throwable reason) {
                        //To change body of implemented methods use File | Settings | File Templates.
                    }

                    @Override
                    public void onSuccess() {
                        FileUploadWindow.getInstance().show();
                    }
                });
            }
        }),new BoxLayoutContainer.BoxLayoutData(new Margins(5)));

        buttons.add(new TextButton("Настройка префиксов", new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent event) {
                GWT.runAsync(new RunAsyncCallback() {
                    @Override
                    public void onFailure(Throwable reason) {
                        //To change body of implemented methods use File | Settings | File Templates.
                    }

                    @Override
                    public void onSuccess() {
                        PrefixConfigWindow.get().show();
                    }
                });
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
