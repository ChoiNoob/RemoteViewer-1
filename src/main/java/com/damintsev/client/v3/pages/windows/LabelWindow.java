package com.damintsev.client.v3.pages.windows;

import com.damintsev.client.devices.Item;
import com.damintsev.client.service.Service2;
import com.damintsev.client.v3.items.Label;
import com.damintsev.client.v3.items.Station;
import com.damintsev.client.v3.items.task.Task;
import com.damintsev.client.v3.items.task.TaskType;
import com.damintsev.client.v3.pages.frames.MonitoringFrame;
import com.damintsev.utils.Dialogs;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.loader.DataProxy;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.BeforeQueryEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.*;

/**
 * User: Damintsev Andrey
 * Date: 14.10.13
 * Time: 23:20
 */
public class LabelWindow implements Editor<Label> {

    private static LabelWindow instance;

    public static LabelWindow get() {
        if(instance == null) instance = new LabelWindow();
        return instance;
    }

    private LabelEditor driver = GWT.create(LabelEditor.class);
    private Window window;
    private TextButton delete;
    private Label label;
    TextArea name;

    private LabelWindow() {
        window = new Window();
        window.setModal(true);
        window.setPixelSize(350, 200);
        window.setHeadingText("Добавить текст");
        ContentPanel con = new ContentPanel();
        con.setHeaderVisible(false);
        con.setBodyStyle("padding: 5px");
        final VerticalLayoutContainer panel = new VerticalLayoutContainer();
        con.add(panel);

        name = new TextArea();
//        name.setHeight(800);
        FieldLabel fieldLabel = new FieldLabel(name, "Имя");
        fieldLabel.setHeight(80);
        panel.add(fieldLabel, new VerticalLayoutContainer.VerticalLayoutData(1,-1));

        delete = new TextButton("Удалить", new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent event) {
                Dialogs.confirm("Будет удалено утройство", new Runnable() {
                    public void run() {
                        Service2.database.deleteLabel(label, new AsyncCallback<Void>() {
                            public void onFailure(Throwable caught) {
                                //To change body of implemented methods use File | Settings | File Templates.
                            }

                            public void onSuccess(Void result) {
                                MonitoringFrame.get().reloadView();
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
                label = driver.flush();
                if (driver.hasErrors()) return;
                window.mask();
                Service2.database.saveItem(label, new AsyncCallback<Item>() {
                    public void onFailure(Throwable caught) {
                        Dialogs.alert("Cannot save device =" + caught.getMessage());
                    }

                    public void onSuccess(Item result) {
                        window.unmask();
                        window.hide();
                        MonitoringFrame.get().add(result);
                        window.hide();
                    }
                });
            }
        }));

        con.addButton(new TextButton("Отмена", new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent event) {
                window.hide();
            }
        }));
        window.setWidget(con);

    }

    public void show(Long id, Runnable run) {
        driver.initialize(this);
        window.show();
        if (id == null) {
            label = new Label();
            delete.hide();
            driver.edit(label);
        } else {
            delete.show();
            window.mask();
            Service2.database.loadLabel(id, new AsyncCallback<Label>() {
                public void onFailure(Throwable caught) {
                    Dialogs.alert("Error loading device =" + caught.getMessage());
                }

                public void onSuccess(Label result) {
                    label = result;
                    window.unmask();
                    driver.edit(label);
                }
            });
        }
    }

    interface LabelEditor extends SimpleBeanEditorDriver<Label, LabelWindow> {

    }
}