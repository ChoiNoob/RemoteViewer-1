package com.damintsev.client.windows;

import com.damintsev.client.devices.CommonDevice;
import com.damintsev.client.devices.Device;
import com.damintsev.client.devices.Station;
import com.damintsev.client.devices.UIItem;
import com.damintsev.client.devices.enums.DeviceType;
import com.damintsev.client.devices.enums.Status;
import com.damintsev.client.service.Service;
import com.damintsev.client.uiframe.UICenterField;
import com.damintsev.utils.Dialogs;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.user.client.rpc.AsyncCallback;
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
public class AddDeviceWindow implements Editor<CommonDevice>{

    private static AddDeviceWindow instance;

    public static AddDeviceWindow get() {
        if(instance == null) instance = new AddDeviceWindow();
        return instance;
    }

    private Driver driver = GWT.create(Driver.class);
    private Window window;
    private TextButton delete;
    private Device device;
    SimpleComboBox<Station> station;
    SimpleComboBox<DeviceType> deviceType;
    TextField name;
    TextField query;
    TextField queryBusy;
    TextArea comment;

    private AddDeviceWindow() {
        window = new Window();
        window.setModal(true);
        window.setPixelSize(350, 350);
        window.setHeadingText("Добавить новый транк");
        ContentPanel con = new ContentPanel();
        con.setHeaderVisible(false);
        con.setBodyStyle("padding: 5px");
        final VerticalLayoutContainer panel = new VerticalLayoutContainer();
        con.add(panel);
        
        station = new SimpleComboBox<Station>(new LabelProvider<Station>() {
            public String getLabel(Station item) {
                return item.getName()==null?"Адрес: " + item.getHost():item.getName() + " (" + item.getHost() + ")";
            }
        });
        for(Station st : UICenterField.get().getStations()) {
            station.add(st);
        }

        station.setTriggerAction(ComboBoxCell.TriggerAction.ALL);
        station.setEditable(false);
        station.setAllowBlank(false);
        panel.add(new FieldLabel(station, "Станция"), new VerticalLayoutContainer.VerticalLayoutData(1,-1));

        deviceType = new SimpleComboBox<DeviceType>(new LabelProvider<DeviceType>() {
            public String getLabel(DeviceType item) {
                return item.getName();
            }
        });
        deviceType.add(DeviceType.IP);
        deviceType.add(DeviceType.ISDN);
        deviceType.setTriggerAction(ComboBoxCell.TriggerAction.ALL);
        deviceType.setEditable(false);
        deviceType.setAllowBlank(false);
        panel.add(new FieldLabel(deviceType, "Тип"), new VerticalLayoutContainer.VerticalLayoutData(1,-1));

        name = new TextField();
        panel.add(new FieldLabel(name, "Имя"), new VerticalLayoutContainer.VerticalLayoutData(1,-1));

        query = new TextField();
        query.setAllowBlank(false);
        panel.add(new FieldLabel(query, "Запрос состояния канала"), new VerticalLayoutContainer.VerticalLayoutData(1,-1));

        queryBusy = new TextField();
        panel.add(new FieldLabel(queryBusy, "Проверака занятых каналов"), new VerticalLayoutContainer.VerticalLayoutData(1,-1));

        comment = new TextArea();
        comment.setHeight(70);
        panel.add(new FieldLabel(comment, "Комментарий"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        delete = new TextButton("Удалить", new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent event) {
                Dialogs.confirm("Будет удалено утройство", new Runnable() {
                    public void run() {
                        Service.instance.deleteDevice(device, new AsyncCallback<Void>() {
                            public void onFailure(Throwable caught) {
                                //To change body of implemented methods use File | Settings | File Templates.
                            }

                            public void onSuccess(Void result) {
                                UICenterField.get().delete(device);
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
                device = driver.flush();
                device.setStatus(Status.INIT);
                if (driver.hasErrors()) return;
                final boolean newEntity = device.getId() == null;
                window.mask();
                Service.instance.saveDevice(device, new AsyncCallback<Device>() {
                    public void onFailure(Throwable caught) {
                       Dialogs.alert("Cannot save device =" +caught.getMessage());
                    }

                    public void onSuccess(Device result) {
                        window.unmask();
                        window.hide();
                        if(newEntity) UICenterField.get().addItem(new UIItem(result));
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
        driver.initialize(this);
    }

    public void show(Long id) {
        window.show();
        if (id == null) {
            device = new CommonDevice();
            delete.hide();
            driver.edit((CommonDevice) device);
        } else {
            delete.show();
            window.mask();
            Service.instance.loadDevice(id, DeviceType.ISDN, new AsyncCallback<Device>() {
                public void onFailure(Throwable caught) {
                    Dialogs.alert("Error loading device =" + caught.getMessage());
                }

                public void onSuccess(Device result) {
                    device = result;
                    window.unmask();
                    driver.edit((CommonDevice) device);
                }
            });
        }

        station.getStore().clear();
        for(Station st : UICenterField.get().getStations()) {
            station.add(st);
        }
    }

    interface Driver extends SimpleBeanEditorDriver<CommonDevice, AddDeviceWindow> {

    }
}
