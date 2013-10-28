package com.damintsev.client.old.devices.windows;

import com.damintsev.client.old.devices.FTPSettings;
import com.damintsev.common.beans.Station;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
import com.sencha.gxt.widget.core.client.form.TextField;

/**
 * User: Damintsev Andrey
 * Date: 15.08.13
 * Time: 0:22
 */
public class UIFTPSettings implements Editor<FTPSettings> {

    private static UIFTPSettings instance;

    public static UIFTPSettings getInstance() {
        if(instance==null)instance=new UIFTPSettings();
        return instance;
    }

    Editor editor = GWT.create(Editor.class);

    private Window window;
    private FTPSettings settings;
    @Path("station")
    SimpleComboBox<Station> simpleComboBox;
    TextField host;
    TextField login;
    TextField password;
    TextField dir;

    private UIFTPSettings(){
        window = new Window();
        window.setPixelSize(350,250);
        ContentPanel contentPanel = new ContentPanel();
        contentPanel.setHeaderVisible(false);
//        contentPanel.se);
        final VerticalLayoutContainer panel = new VerticalLayoutContainer();
        panel.setLayoutData(new MarginData(5));
        contentPanel.add(panel);

        simpleComboBox = new SimpleComboBox<Station>(new LabelProvider<Station>() {
            public String getLabel(Station item) {
                return item.getName()==null?"Адрес: " + item.getHost():item.getName() + " (" + item.getHost() + ")";
            }
        });
        simpleComboBox.setTriggerAction(ComboBoxCell.TriggerAction.ALL);
        simpleComboBox.setEditable(false);
        simpleComboBox.setAllowBlank(false);
        simpleComboBox.setEnabled(false);
        panel.add(new FieldLabel(simpleComboBox, "Станция"), new VerticalLayoutContainer.VerticalLayoutData(1,-1));

        host = new TextField();
        host.setAllowBlank(false);
        panel.add(new FieldLabel(host, "Адрес"), new VerticalLayoutContainer.VerticalLayoutData(1,-1));

        login = new TextField();
        login.setAllowBlank(false);
        panel.add(new FieldLabel(login, "Логин"), new VerticalLayoutContainer.VerticalLayoutData(1,-1));

        password = new TextField();
        login.setAllowBlank(false);
        panel.add(new FieldLabel(password, "Пароль"), new VerticalLayoutContainer.VerticalLayoutData(1,-1));

        dir = new TextField();
        dir.setAllowBlank(false);
        panel.add(new FieldLabel(dir, "Путь к файлу"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));
        window.add(contentPanel);
        window.addButton(new TextButton("Сохранить", new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent event) {
                editor.flush();
                if(editor.hasErrors())return;
//                Service.instance.saveFTPSettings(editor.flush(), new AsyncCallback<FTPSettings>() {
//                    public void onFailure(Throwable caught) {
//                        Dialogs.alert(caught.getMessage());
//                    }
//
//                    public void onSuccess(FTPSettings result) {
//                        Dialogs.message("Сохранение прошло успешно ИД = " +result.getId(), new Runnable() {
//                            public void run() {
//                                window.hide();
//                            }
//                        });
//                    }
//                });
            }
        }));
        window.addButton(new TextButton("Отмена", new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent event) {
                window.hide();
            }
        }));
        editor.initialize(this);
    }

    public void show(final Station station) {
        window.show();
        window.mask();
        simpleComboBox.add(station);
        simpleComboBox.setValue(station);
//        Service.instance.loadFTPSettings(station, new AsyncCallback<FTPSettings>() {
//            public void onFailure(Throwable caught) {
//                Dialogs.alert(caught.getMessage());
//            }
//
//            public void onSuccess(FTPSettings result) {
//                window.unmask();
//                System.out.println("call edit!!!!!!"+ result);
//                if(result==null) result = new FTPSettings();
//                result.setStation(station);
//                UIFTPSettings.this.settings = result;
//                editor.edit(result);
//            }
//        });
    }

    interface Editor extends SimpleBeanEditorDriver<FTPSettings, UIFTPSettings> {
    }
}
