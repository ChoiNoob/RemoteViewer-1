package com.damintsev.client.uiframe;

import com.damintsev.client.Utils;
import com.damintsev.client.dao.DeviceType;
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
public class AddSourceWindow extends Window {

    private static AddSourceWindow instance;

    public static AddSourceWindow get() {
        if(instance == null) instance = new AddSourceWindow();
        return instance;
    }

    private AddSourceWindow() {
        setModal(true);
        setPixelSize(350, 350);
        setHeadingText("Добавить новый транк");
        ContentPanel con = new ContentPanel();
        con.setHeaderVisible(false);
        con.setBodyStyle("padding: 5px");
        final VerticalLayoutContainer panel = new VerticalLayoutContainer();
        con.add(panel);

        SimpleComboBox<DeviceType> deviceType = new SimpleComboBox<DeviceType>(new LabelProvider<DeviceType>() {
            public String getLabel(DeviceType item) {
                return item.name();
            }
        });
        deviceType.add(DeviceType.IP);
        deviceType.add(DeviceType.ISDN);

        panel.add(deviceType, new VerticalLayoutContainer.VerticalLayoutData(1,-1));

        final TextField name = new TextField();
        panel.add(new FieldLabel(name, "Имя"), new VerticalLayoutContainer.VerticalLayoutData(1,-1));

        TextField host = new TextField();
        host.setAllowBlank(false);
        panel.add(new FieldLabel(host, "Строка зпроса"), new VerticalLayoutContainer.VerticalLayoutData(1,-1));

        TextField port = new TextField();
        port.setAllowBlank(false);
        panel.add(new FieldLabel(port, "Порт"), new VerticalLayoutContainer.VerticalLayoutData(1,-1));

        TextField login = new TextField();
        panel.add(new FieldLabel(login, "Логин"), new VerticalLayoutContainer.VerticalLayoutData(1,-1));
        panel.add(new FieldLabel(new TextField(), "Пароль"), new VerticalLayoutContainer.VerticalLayoutData(1,-1));
        TextArea comment = new TextArea();
        comment.setHeight(70);
        panel.add(new FieldLabel(comment, "Комментарий"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        con.addButton(new TextButton("Сохранить", new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent event) {
                UIItem device = new UIItem();
                device.setName(name.getValue());
                device.setImage(Utils.getImage("cloud_130"));
                UICenterField.get().addItem(device);
                hide();
            }  //todo
        }));

        con.addButton(new TextButton("Отмена", new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent event) {
                hide();
            }
        }));

        setWidget(con);
    }
}
