package com.damintsev.client.uiframe;

import com.damintsev.client.devices.ISDN;
import com.damintsev.client.devices.Station;
import com.damintsev.client.devices.enums.DeviceType;
import com.damintsev.client.devices.UIItem;
import com.damintsev.utils.Dialogs;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.form.TextField;

/**
 * User: Damintsev Andrey
 * Date: 06.08.13
 * Time: 1:40
 */
public class AddDeviceWindow implements Editor<Station>{

    private static AddDeviceWindow instance;

    public static AddDeviceWindow get() {
        if(instance == null) instance = new AddDeviceWindow();
        return instance;
    }
     Station station;
    private Driver driver = GWT.create(Driver.class);
    private Window window;
    //editor fields
    TextField name;
    TextField host;
    TextField port;
    TextField login;
    TextField password;
    TextArea comment;

    private AddDeviceWindow() {
        station = new Station();
        window = new Window();
        window.setModal(true);
        window.setPixelSize(350, 350);
        window.setHeadingText("Добавить новый транк");
        ContentPanel con = new ContentPanel();
        con.setHeaderVisible(false);
        con.setBodyStyle("padding: 5px");
        final VerticalLayoutContainer panel = new VerticalLayoutContainer();
        con.add(panel);
        
        final SimpleComboBox<Station> stations = new SimpleComboBox<Station>(new LabelProvider<Station>() {
            public String getLabel(Station item) {
                return item.getName()==null?"Адрес: " + item.getHost():item.getName() + " (" + item.getHost() + ")";
            }
        });
        for(Station station : UICenterField.get().getStations()) {
            stations.add(station);
        }
        stations.setTriggerAction(ComboBoxCell.TriggerAction.ALL);
        stations.setEditable(false);
        stations.setAllowBlank(false);

        panel.add(new FieldLabel(stations, "Станция"), new VerticalLayoutContainer.VerticalLayoutData(1,-1));

        SimpleComboBox<DeviceType> deviceType = new SimpleComboBox<DeviceType>(new LabelProvider<DeviceType>() {
            public String getLabel(DeviceType item) {
                return item.name();
            }
        });
        deviceType.add(DeviceType.IP);
        deviceType.add(DeviceType.ISDN);
        deviceType.setTriggerAction(ComboBoxCell.TriggerAction.ALL);
        deviceType.setEditable(false);

        panel.add(new FieldLabel(deviceType, "Тип"), new VerticalLayoutContainer.VerticalLayoutData(1,-1));

        name = new TextField();
        panel.add(new FieldLabel(name, "Имя"), new VerticalLayoutContainer.VerticalLayoutData(1,-1));

        host = new TextField();
        host.setAllowBlank(false);
        panel.add(new FieldLabel(host, "Строка зпроса"), new VerticalLayoutContainer.VerticalLayoutData(1,-1));

        port = new TextField();
        port.setAllowBlank(false);
        panel.add(new FieldLabel(port, "Порт"), new VerticalLayoutContainer.VerticalLayoutData(1,-1));

        login = new TextField();
        panel.add(new FieldLabel(login, "Логин"), new VerticalLayoutContainer.VerticalLayoutData(1,-1));
        password = new TextField();
        panel.add(new FieldLabel(password, "Пароль"), new VerticalLayoutContainer.VerticalLayoutData(1,-1));

        comment = new TextArea();
        comment.setHeight(70);
        panel.add(new FieldLabel(comment, "Комментарий"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        con.addButton(new TextButton("Сохранить", new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent event) {
//                if(!stations.isValid())return;
//                ISDN isdn = new ISDN();
//                isdn.setParentStation(stations.getValue());
//
//                UIItem<ISDN> cloud = new UIItem<ISDN>(isdn);
//                UICenterField.get().addItem(cloud);
//                window.hide();

                driver.flush();
                Station sss = driver.flush();
                Dialogs.alert("host=" + sss.getHost());
                Dialogs.alert("port=" + sss.getPort());
            }
        }));

        con.addButton(new TextButton("Отмена", new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent event) {
                window.hide();
            }
        }));

        driver.initialize(this);

        window.setWidget(con);
    }

    public void show() {
        window.show();
        driver.edit(station);
    }

    interface Driver extends SimpleBeanEditorDriver<Station, AddDeviceWindow> {

    }
}
