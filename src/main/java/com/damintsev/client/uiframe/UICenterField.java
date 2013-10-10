package com.damintsev.client.uiframe;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.drop.AbsolutePositionDropController;
import com.allen_sauer.gwt.dnd.client.drop.DropController;
import com.damintsev.client.devices.Device;
import com.damintsev.client.devices.Item;
import com.damintsev.client.devices.Station;
import com.damintsev.client.devices.UIItem;
import com.damintsev.client.devices.enums.DeviceType;
import com.damintsev.client.devices.enums.Status;
import com.damintsev.client.service.Service;
import com.damintsev.client.service.Service2;
import com.damintsev.utils.Dialogs;
import com.damintsev.utils.Position;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;

import java.util.*;

/**
 * User: Damintsev Andrey
 * Date: 01.08.13
 * Time: 23:17
 */
public class UICenterField {

    private static UICenterField instance;

    public static UICenterField get() {
        if (instance == null) instance = new UICenterField();
        return instance;
    }

    private PickupDragController dragController;
    private AbsolutePanel panel;
//    private Map<Long, UIItem> uiStations;
//    private Map<Long, UIItem> uiDevices;
    private Map<String, UIItem> uiItems;

    private UICenterField() {
        uiItems = new HashMap<String, UIItem>();
//        uiStations = new HashMap<Long, UIItem>();
//        uiDevices = new HashMap<Long, UIItem>();
        panel = new AbsolutePanel();
        dragController = new PickupDragController(panel, true) {
            @Override
            public void dragEnd() {
                drawConnections(false);
                super.dragEnd();
            }

            @Override
            public void dragStart() {
                clearCanvas();
                super.dragStart();
            }
        };
        dragController.setBehaviorConstrainedToBoundaryPanel(false);
        DropController dropController = new AbsolutePositionDropController(panel);
        dragController.registerDropController(dropController);
        dragController.setBehaviorMultipleSelection(true);

        final TextButton editButton = new TextButton("Редактировать", new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent event) {
                allowDrag();
                UISettingsPanel.get().expand();
                stop();
            }
        });
        editButton.setAllowTextSelection(false);
        editButton.getElement().getStyle().setTop(5, Style.Unit.PX);
        editButton.getElement().getStyle().setRight(5, Style.Unit.PX);
        editButton.getElement().getStyle().setPosition(Style.Position.ABSOLUTE);

        panel.add(editButton);
        drawCanvas(panel);
        loadFromDatabase();
    }

    private Canvas canvas;

    private void drawCanvas(AbsolutePanel panel) {
        final int height = Window.getClientHeight();
        final int width = Window.getClientWidth();

        canvas = Canvas.createIfSupported();
        canvas.setWidth(width + "px");
        canvas.setHeight(height + "px");
        canvas.setCoordinateSpaceWidth(width);
        canvas.setCoordinateSpaceHeight(height);
        panel.add(canvas);
    }

    public Widget getContent() {
        return panel;
    }

    public void addItem(UIItem item) {
        addItem(item, false);
    }

    public void addItem(UIItem item, boolean fromDB) {
       insertUpdateItem(item);

        dragController.makeDraggable(item);
        System.out.println("Asdas=" + panel.getWidgetIndex(item));
        if(panel.getWidgetIndex(item) == -1 || fromDB)
            panel.add(item, 0, 0);
        if (item.getPosition() == null) {
            int centerX = Window.getClientWidth() / 2 - item.getWidth() / 2;
            int centerY = Window.getClientHeight() / 2 - item.getHeight() / 2;
            panel.setWidgetPosition(item, centerX, centerY);
        } else {
            Position pos = item.getPosition();
            panel.setWidgetPosition(item, pos.x, pos.y);
        }
        drawConnections(false);
    }

    private UIItem insertUpdateItem(UIItem item) {
//        if (item.getDeviceType() == DeviceType.STATION) {
//            System.out.println("CPT=" + uiStations.containsKey(item.getId()));
////            if (uiStations.containsKey(item.getId())) {
////                item = uiStations.get(item.getId());
////                item.setData(item.getData());
////            } else
//                uiStations.put(item.getId(), item);
//        } else {
////            if (uiDevices.containsKey(item.getId())) {
////                item = uiDevices.get(item.getId());
////                item.setData(item.getData());
////            } else
//                uiDevices.put(item.getId(), item);
//        }
        return item;
    }

    private void allowDrag() {
        for(UIItem item : uiItems.values()) {
            dragController.makeDraggable(item);
        }
    }

    private void disAllowDrag() {
        for(UIItem item : uiItems.values()) {
            dragController.makeNotDraggable(item);
        }
    }

    public void saveItemPositions() {
//        dragController.clearSelection();
//        disAllowDrag();
//        for(UIItem station : uiStations.values()) {
//            station.savePosition();
//        }
//        for(UIItem device : uiDevices.values()) {
//            device.savePosition();
//        }
//        drawConnections(false);
//        saveToDatabase();   //todo!
//        start();
//        schedule();
    }

    public void saveToDatabase() {
//        List<Item> items = new ArrayList<Item>();
//        for(UIItem station : uiStations.values()) {
//            items.add(new Item<Device>(station.getData(),station.getPosition()));
//        }
//        for(UIItem device : uiDevices.values()) {
//            items.add(new Item<Device>(device.getData(),device.getPosition()));
//        }
//        Service.instance.saveItems(items, new AsyncCallback<Boolean>() {
//            public void onFailure(Throwable throwable) {
//                Dialogs.alert("Error saving properties! " + throwable.getMessage());
//            }
//
//            public void onSuccess(Boolean bool) {
//            }
//        });
    }

    public Widget getSelected() {
        Iterator<Widget> it = dragController.getSelectedWidgets().iterator();
        return it.hasNext() ? it.next() : null;
    }

//    public void delete(Device device) {
//        delete(device, true);
//    }
//
//    public void delete(Device device, boolean deleteChilds) {
//        if(device instanceof Station) {
//            dragController.makeNotDraggable(uiStations.get(device.getId()));
//            panel.remove(uiStations.get(device.getId()));
//            uiStations.remove(device.getId());
//            if(deleteChilds)
//                for (UIItem dev : findDevicesForStation(device.getId())) {
//                    delete(dev.getData());
//                }
//        } else {
//            dragController.makeNotDraggable(uiDevices.get(device.getId()));
//            panel.remove(uiDevices.get(device.getId()));
//            uiDevices.remove(device.getId());
//        }
//        drawConnections(false);
//    }

//    List<UIItem> findDevicesForStation(UIItem station){
//        return findDevicesForStation(station.getId());
//    }

//    List<UIItem> findDevicesForStation(Station station) {
//        return findDevicesForStation(station.getId());
//    }

//    List<UIItem> findDevicesForStation(Long deviceId) {
//        List<UIItem> devices = new ArrayList<UIItem>();
//        for(UIItem item : uiDevices.values()) {
//            if(item.getStation().getId().equals(deviceId))
//                devices.add(item);
//        }
//        return devices;
//    }

//    private void loadFromDatabase() {
//        System.out.println("load from database!");
//        Service.instance.loadItems(new AsyncCallback<List<Item>>() {
//            public void onFailure(Throwable throwable) {
//                Dialogs.alert("Error loading from database! " + throwable.getMessage());
//            }
//
//            public void onSuccess(List<Item> items) {
//                System.out.println("loaded " + items.size());
//                uiDevices.clear();
//                uiStations.clear();
//                for (Item item : items) {
//                    System.out.println("loading from db id=" + item.getId() + " x=" + item.getCoordX() + " y=" + item.getCoordY());
//                    Position pos = new Position(item.getCoordX(), item.getCoordY());
//                    addItem(new UIItem(item.getData(), pos), true);
//                }
//                if (items.size() != 0) {
//                    start();
//                    schedule();
//                    disAllowDrag();
//                }
//                drawConnections(false);
//            }
//        });
//    }

    private void loadFromDatabase() {
        System.out.println("load from database!");
        Service2.database.loadUIItems(new AsyncCallback<List<Item>>() {
            public void onFailure(Throwable throwable) {
                Dialogs.alert("Error loading from database! " + throwable.getMessage());
            }

            public void onSuccess(List<Item> items) {
                System.out.println("loaded " + items.size());
                uiItems.clear();
//                uiDevices.clear();
//                uiStations.clear();
                for (Item item : items) {
                    System.out.println("loading from db id=" + item + " x=");// + item.getCoordX() + " y=" + item.getCoordY());
//                    Position pos = new Position(item.getCoordX(), item.getCoordY());
                    addItem(new UIItem(item), true);
                }
                if (items.size() != 0) {
                    start();
                    schedule();
                    disAllowDrag();
                }
                drawConnections(false);
            }
        });
    }

    public void revertItemPositions() {
        dragController.clearSelection();
        disAllowDrag();
        drawConnections(false);
    }

    private void drawConnections(boolean fireEvent) {
        clearCanvas();
        for(UIItem station : uiItems.values()) {
            station.setLabelColor();
//            for(UIItem device : findDevicesForStation(station)) {
//                drawLine(station.getCenterPosition(), device.getCenterPosition(), device.getStatus());
//            }
        }
    }

    public void drawLine(Position from, Position to, Status status) {
        if (from.x == to.x && from.y == to.y) return;
        Context2d context = canvas.getContext2d();
        context.beginPath();
        context.setLineWidth(3);
        System.out.println("draw s= " + status + " color=" + status.getColor());
        context.setStrokeStyle(status.getColor());
        context.moveTo(from.x, from.y);
        context.lineTo(to.x, to.y);
        context.stroke();
    }

    private void clearCanvas() {
        Context2d context = canvas.getContext2d();
        context.clearRect(0, 0, Window.getClientWidth(), Window.getClientHeight());
    }

//    public List<Station> getStations() {
//        List<Station> stations = new ArrayList<Station>();
//        for (UIItem st : uiStations.values()) {
//            stations.add((Station) st.getData());
//        }
//        return stations;
//    }

    public void schedule() {
        Scheduler.get().scheduleFixedPeriod(new Scheduler.RepeatingCommand() {
            public boolean execute() {
                scheduler();
                return start;
            }
        }, 5000);
    }

    private void scheduler() {
//        if (iterator == null)
//            iterator = createIterator();
//        if (iterator.hasNext()) {
//            final Device device = iterator.next();
//            Service.instance.checkDevice(device, new AsyncCallback<Device>() {
//                public void onFailure(Throwable caught) {
//                    stop();
//                    Dialogs.alert("Check device false! " + caught.getMessage());
//                }
//
//                public void onSuccess(Device result) {
//                    if (result != null) {
//                        System.out.println("result not null=" + result.getStatus());
//                        updateUIItem(result);
//                    }
//                }
//            });
//        } else {
//            iterator = createIterator();
//        }
//        Service.instance.getItemsState(new AsyncCallback<List<Device>>() {
//            public void onFailure(Throwable caught) {
//                stop();
//                Dialogs.alert("Error loading from DB at scheduler =" + caught.getMessage());
//            }
//
//            public void onSuccess(List<Device> result) {
//                for (Device device : result) {
//                    updateUIItem(device);
//                }
//                drawConnections(false);
//            }
//        });
    }

//    private void updateUIItem(Device device) {
//        if(device instanceof Station) {
//            System.out.println("update ST");
//            UIItem station = uiStations.get(device.getId());
//            if(station == null) {stop();reload();return;}
//            station.setData(device);
//            uiStations.put(device.getId(), station);
//        } else {
//            System.out.println("update DEV=" + device.getStatus());
//            UIItem dev = uiDevices.get(device.getId());
//            if(dev == null) {stop();reload();return;}
//            dev.setData(device);
//            uiDevices.put(device.getId(), dev);
//        }
//        drawConnections(false);
//    }

    private Iterator<Device> iterator;

//    private Iterator<Device> createIterator() {
//        List<Device> items = new ArrayList<Device>();
//        for(UIItem station : uiStations.values()) {
//            items.add(station.getData());
//        }
//        for(UIItem device : uiDevices.values()) {
//            items.add(device.getData());
//        }
//        return items.iterator();
//    }

    private boolean start;

    public void start() {
        this.start = true;
    }

    public void stop() {
        System.out.println("stop");
        this.start = false;
    }

    public void delete(Device device) {
//        if(device instanceof Station) {
//            UIItem station = uiStations.get(device.getId());
////            dragController.makeNotDraggable(station);
//            panel.remove(station);
//            for (UIItem dev : findDevicesForStation(device.getId())) {
//                delete(dev.getData());
//            }
//        }else {
//            UIItem dev = uiDevices.get(device.getId());
////            dragController.makeNotDraggable(dev);
//            panel.remove(dev);
//        }
//        drawConnections(false);
//        createIterator();
    }

    public static native void reload()/*-{
        $wnd.location = $wnd.location.pathname;
    }-*/;
}