package com.damintsev.client.v3.pages.windows;

import com.damintsev.client.old.devices.Item;
import com.damintsev.client.service.Service;
import com.damintsev.common.pojo.Station;
import com.damintsev.client.v3.pages.frames.MonitoringFrame;
import com.damintsev.common.utils.Dialogs;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.*;

/**
 * User: Damintsev Andrey
 * Date: 03.08.13
 * Time: 1:01
 */
public class AddStationWindow implements Editor<Station> {

    private static AddStationWindow instance;

    public static AddStationWindow getInstance() {
//        if (instance == null)
            instance = new AddStationWindow();
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
    NumberField<Integer> delay;
//    @Path(value = "allowStatistics")
//    CheckBox checkBox;

    private AddStationWindow() {
        window = new Window();
        window.setModal(true);
        window.setPixelSize(400, 370);
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

        //todo биллинг отключен
//        checkBox = new CheckBox();
//        FieldLabel label = new FieldLabel(checkBox, "Разрешить сбор статистики");
//        label.setLabelWidth(300);
//        panel.add(label, new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        delay = new NumberField<Integer>(new NumberPropertyEditor.IntegerPropertyEditor());
        FieldLabel label = new FieldLabel(delay, "Период опроса станции в секундах");
//        label.setWidth(200);
        label.setLabelWidth(250);
        panel.add(label, new VerticalLayoutContainer.VerticalLayoutData(1, 1, new Margins(-1,-1,-1,1)));

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
                        Service.instance.deleteStation(station, new AsyncCallback<Void>() {
                            public void onFailure(Throwable caught) {
                                //todo realize
                            }

                            public void onSuccess(Void result) {
//                                MonitoringFrame.getInstance().reloadView();//todo realize deletion
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
                Service.instance.saveItem(station, new AsyncCallback<Item>() {
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
            Service.instance.loadStation(stationId, new AsyncCallback<Station>() {
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
