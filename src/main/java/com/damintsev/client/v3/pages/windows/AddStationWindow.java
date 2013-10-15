package com.damintsev.client.v3.pages.windows;

import com.damintsev.client.devices.Device;
import com.damintsev.client.devices.Item;
import com.damintsev.client.service.Service2;
import com.damintsev.client.v3.items.Station;
import com.damintsev.client.devices.enums.DeviceType;
import com.damintsev.client.devices.enums.Status;
import com.damintsev.client.service.Service;
import com.damintsev.client.v3.pages.frames.MonitoringFrame;
import com.damintsev.client.windows.UIFTPSettings;
import com.damintsev.utils.Dialogs;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
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
        panel.add(new FieldLabel(name, "�?мя"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

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

        //todo вернуть эту кнопку
//        con.addButton(new TextButton("Сбор данных о звонках", new SelectEvent.SelectHandler() {
//            public void onSelect(SelectEvent event) {
//                station = editor.flush();
//                if (editor.hasErrors()) return;
//                station.setStatus(Status.INIT);
////                TelnetWindow.getInstance().show(station);
//
//                  UIFTPSettings.getInstance().show(station);
//            }
//        }));
        delete = new TextButton("Удалить", new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent event) {
                Dialogs.confirm("Будет удалена станция и все связанные с ней обькты", new Runnable() {
                    public void run() {
                        Service2.database.deleteStation(station, new AsyncCallback<Void>() {
                            public void onFailure(Throwable caught) {
                                //todo realize
                            }

                            public void onSuccess(Void result) {
//                                MonitoringFrame.get().reloadView();//todo realize deletion
                                MonitoringFrame.get().delete(station);//todo realize deletion
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
                window.mask();
                Service2.database.saveItem(station, new AsyncCallback<Item>() {
                    public void onFailure(Throwable caught) {
                        Dialogs.alert("Error saving station to db " + caught.getMessage());
                    }

                    public void onSuccess(Item result) {
                        window.unmask();
                        station = (Station) result;
                        MonitoringFrame.get().add(result);
                        window.hide();

                        if (listener != null)
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
        editor.initialize(this);
        if (stationId == null) {
            station = new Station();
            delete.hide();
            editor.edit(station);
        } else {
            window.mask();
            Service2.database.loadStation(stationId, new AsyncCallback<Station>() {
                public void onFailure(Throwable caught) {
                    Dialogs.alert("Error while loading Station =" + caught.getMessage());
                }

                public void onSuccess(Station result) {
                    window.unmask();
                    AddStationWindow.this.station =  result;
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
