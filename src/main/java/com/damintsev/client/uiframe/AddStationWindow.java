package com.damintsev.client.uiframe;

import com.damintsev.client.Utils;
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
public class AddStationWindow extends Window {

    private static AddStationWindow instance;

    public static AddStationWindow get() {
        if(instance == null) instance = new AddStationWindow();
        return instance;
    }

    private AddStationWindow() {
        setModal(true);
        setPixelSize(350, 300);
        setHeadingText("Добавить новую телефонную станцию");
        ContentPanel con = new ContentPanel();
        con.setHeaderVisible(false);
        con.setBodyStyle("padding: 5px");
        final VerticalLayoutContainer panel = new VerticalLayoutContainer();
        con.add(panel);


       final TextField name = new TextField();
        panel.add(new FieldLabel(name, "Имя"), new VerticalLayoutContainer.VerticalLayoutData(1,-1));

        TextField host = new TextField();
        host.setAllowBlank(false);
        panel.add(new FieldLabel(host, "Адрес сервера"), new VerticalLayoutContainer.VerticalLayoutData(1,-1));

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
                UIItem stationItem = new UIItem();
                stationItem.setName(name.getValue());
                stationItem.setImage(Utils.getImage("hipath"));
                UICenterField.get().addItem(stationItem);
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
