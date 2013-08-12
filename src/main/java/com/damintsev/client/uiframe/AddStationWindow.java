package com.damintsev.client.uiframe;

import com.damintsev.client.devices.Station;
import com.damintsev.client.devices.UIItem;
import com.damintsev.client.devices.enums.Status;
import com.damintsev.client.service.Service;
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
    TextField name;
    TextField host;
    TextField port;
    TextField login;
    TextField password;
    TextArea comment;

    private AddStationWindow() {
        window = new Window();
        window.setModal(true);
        window.setPixelSize(350, 300);
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

        con.addButton(new TextButton("Тест", new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent event) {
                station = editor.flush();
                if (editor.hasErrors()) return;
                station.setStatus(Status.INIT);
                TelnetWindow.getInstance().show(station);
            }
        }));
        delete = new TextButton("Удалить", new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent event) {
                Dialogs.confirm("Будет удалена станция и все связанные с ней обькты", new Runnable() {
                    public void run() {
                        UICenterField.get().delete(station);
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
                UICenterField.get().addItem(new UIItem<Station>(station));
                window.hide();
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

    public void show(Station station) {
        if (station == null) {
            station = new Station();
            delete.hide();
        } else delete.show();
        this.station = station;
        editor.edit(station);
        window.show();
    }

    interface StationEdit extends SimpleBeanEditorDriver<Station, AddStationWindow> {

    }
}
