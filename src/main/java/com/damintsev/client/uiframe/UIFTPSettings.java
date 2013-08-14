package com.damintsev.client.uiframe;

import com.damintsev.client.devices.FTPSettings;
import com.damintsev.client.service.Service;
import com.damintsev.utils.Dialogs;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
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
                Service.instance.saveFTPSettings(editor.flush(), new AsyncCallback<Void>() {
                    public void onFailure(Throwable caught) {
                        Dialogs.alert(caught.getMessage());
                    }

                    public void onSuccess(Void result) {
                    }
                });
                window.hide();
            }
        }));
        window.addButton(new TextButton("Отмена", new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent event) {
                window.hide();
            }
        }));
        editor.initialize(this);
    }

    public void show() {
        Service.instance.loadFTPSettings(new AsyncCallback<FTPSettings>() {
            public void onFailure(Throwable caught) {
                Dialogs.alert(caught.getMessage());
            }

            public void onSuccess(FTPSettings result) {
                System.out.println("call edit!!!!!!"+ result);
                if(result==null) result = new FTPSettings();
                UIFTPSettings.this.settings = result;
                editor.edit(result);
            }
        });
        window.show();
    }

    interface Editor extends SimpleBeanEditorDriver<FTPSettings, UIFTPSettings> {

    }
}
