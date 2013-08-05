package com.damintsev.client.uiframe;

import com.damintsev.client.Utils;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.user.client.ui.Image;
import com.sencha.gxt.core.client.util.IconHelper;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.AbstractHtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FileUploadField;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.form.TextField;

import static com.sencha.gxt.widget.core.client.container.AbstractHtmlLayoutContainer.HtmlData;

/**
 * User: Damintsev Andrey
 * Date: 03.08.13
 * Time: 1:01
 */
public class AddDeviceWindow extends Window {

    private static AddDeviceWindow instance;

    public static AddDeviceWindow get() {
        if(instance == null) instance = new AddDeviceWindow();
        return instance;
    }

//    private AddDeviceWindow(){
//        setModal(true);
//        setPixelSize(420,300);
//        setHeadingText("Добавление нового параметра");
//        ContentPanel con = new ContentPanel();
//        con.setWidth(420);
//        HtmlLayoutContainer panel= new HtmlLayoutContainer(getTableMarkup());
//        int cw = (COLUMN_FORM_WIDTH / 2) - 12;
//        con.add(panel);
////        panel.setHeaderVisible(false);
////        VerticalLayoutContainer container = new VerticalLayoutContainer();
////        panel.add(container);
//        TextField host = new TextField();
//        host.setWidth(cw);
//        panel.add(new FieldLabel(host, "Name"), new HtmlData(".text"));
//
//        Image logo = new Image(IconHelper.getImageResource(UriUtils.fromString("/web/img/avanti_logo_64.png"), 64, 64));
//        panel.add(new FieldLabel(logo), new HtmlData(".image"));
//        panel.add(new FieldLabel(new FileUploadField()), new HtmlData(".upload"));
//
//        setWidget(con);
//    }
//
//    private native String getTableMarkup() /*-{
//        return [ '<table width=100% cellpadding=0 cellspacing=0>',
//            '<tr><td class=image width=40% ></td><td class=text width=60%></td></tr>',
//            '<tr><td class=upload></td><td class=email></td></tr>',
//            '<tr><td class=birthday></td><td class=user></td></tr>',
//            '<tr><td class=editor colspan=2></td></tr>', '</table>'
//
//        ].join("");
//    }-*/;


    private AddDeviceWindow() {

        setModal(true);
        setPixelSize(350, 300);
        setHeadingText("Добавить новую телефонную станцию");
        ContentPanel con = new ContentPanel();
        con.setHeaderVisible(false);
        con.setBodyStyle("padding: 5px");
        final VerticalLayoutContainer panel = new VerticalLayoutContainer();
        con.add(panel);

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
                Station station = new Station();
                station.setResource(Utils.getImage("hipath"));
                UICenterField.get().addItem(station);
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
