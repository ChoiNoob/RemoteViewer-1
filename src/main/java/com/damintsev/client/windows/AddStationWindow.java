package com.damintsev.client.windows;

import com.damintsev.client.devices.Device;
import com.damintsev.client.devices.Station;
import com.damintsev.client.devices.UIItem;
import com.damintsev.client.devices.enums.DeviceType;
import com.damintsev.client.devices.enums.Status;
import com.damintsev.client.service.Service;
import com.damintsev.client.uiframe.TelnetWindow;
import com.damintsev.client.uiframe.UICenterField;
import com.damintsev.utils.Dialogs;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.form.TextField;

/**
 * User: Damintsev Andrey
 * Date: 03.08.13
 * Time: 1:01
 */
public class AddStationWindow implements Editor<Station> {

    private static AddStationWindow instance;

    public static AddStationWindow get() {
        if (instance == null) instance = new AddStationWindow();
        return instance;
    }

    private Window window;
    private StationEdit editor = GWT.create(StationEdit.class);
    private Station station;
    private TextButton delete;
    private Runnable listener;
    TextField name;
    TextField host;
    TextField port;
    TextField login;
    TextField password;
    TextArea comment;
    @Path(value = "allowStatistics")
    CheckBox checkBox;

    private AddStationWindow() {
        window = new Window();
        window.setModal(true);
        window.setPixelSize(400, 350);
        window.setHeadingText("Добавить новую телефонную станцию");
        ContentPanel con = new ContentPanel();
        con.setHeaderVisible(false);
        con.setBodyStyle("padding: 5px");
        final VerticalLayoutContainer panel = new VerticalLayoutContainer();
        con.add(panel);

        name = new TextField();
        panel.add(new FieldLabel(name, "Имя"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        host = new TextField();
        host.setAllowBlank(false);
        panel.add(new FieldLabel(host, "Адрес сервера"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        port = new TextField();
        panel.add(new FieldLabel(port, "Порт"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        login = new TextField();
        panel.add(new FieldLabel(login, "Логин"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        password = new TextField();
        panel.add(new FieldLabel(password, "Пароль"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));
        comment = new TextArea();
        comment.setHeight(70);
        panel.add(new FieldLabel(comment, "Комментарий"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        checkBox = new CheckBox();
        panel.add(new FieldLabel(checkBox, "Разрешить сбор статистики"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        con.addButton(new TextButton("Сбор данных о звонках", new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent event) {
                station = editor.flush();
                if (editor.hasErrors()) return;
                station.setStatus(Status.INIT);
//                TelnetWindow.getInstance().show(station);

                  UIFTPSettings.getInstance().show(station);
            }
        }));
        delete = new TextButton("Удалить", new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent event) {
                Dialogs.confirm("Будет удалена станция и все связанные с ней обькты", new Runnable() {
                    public void run() {
//
                        Service.instance.deleteDevice(station, new AsyncCallback<Void>() {
                            public void onFailure(Throwable caught) {
                                //To change body of implemented methods use File | Settings | File Templates.
                            }

                            public void onSuccess(Void result) {
                                UICenterField.get().delete(station);
                            }
                        });
                        window.hide();
                    }
                });
            }
        });
        delete.hide();
        con.addButton(delete);

        con.addButton(new TextButton("Сохранить", new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent event) {
                station = editor.flush();
                if (editor.hasErrors()) return;
                station.setStatus(Status.INIT);
                final boolean newInstance = station.getId()==null;
                window.mask();
                Service.instance.saveDevice(station, new AsyncCallback<Device>() {
                    public void onFailure(Throwable caught) {
                        Dialogs.alert("Error saving station to db " + caught.getMessage());
                    }

                    public void onSuccess(Device result) {
                        window.unmask();
                        window.hide();
//                        if(newInstance)UICenterField.get().addItem(new UIItem(result));
                        if(listener != null)
                            listener.run();
                    }
                });
            }
        }));

        con.addButton(new TextButton("Отмена", new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent event) {
                window.hide();
            }
        }));
        editor.initialize(this);
        window.setWidget(con);
    }

    public void show(Long stationId, Runnable listener) {
        this.listener = listener;
        if (stationId == null) {
            station = new Station();
            delete.hide();
            editor.edit(station);
        } else {
            window.mask();
            Service.instance.loadDevice(stationId, DeviceType.STATION, new AsyncCallback<Device>() {
                public void onFailure(Throwable caught) {
                    Dialogs.alert("Error while loading Station =" + caught.getMessage());
                }

                public void onSuccess(Device result) {
                    window.unmask();
                    AddStationWindow.this.station = (Station) result;
                    editor.edit(station);
                }
            });
            delete.show();
        }
        window.show();
    }

    interface StationEdit extends SimpleBeanEditorDriver<Station, AddStationWindow> {

    }
}
