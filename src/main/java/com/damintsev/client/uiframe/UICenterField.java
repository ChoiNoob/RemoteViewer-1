package com.damintsev.client.uiframe;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.drop.AbsolutePositionDropController;
import com.allen_sauer.gwt.dnd.client.drop.DropController;
import com.damintsev.client.devices.Device;
import com.damintsev.client.devices.Station;
import com.damintsev.client.devices.enums.DeviceType;
import com.damintsev.client.devices.Item;
import com.damintsev.client.devices.UIItem;
import com.damintsev.client.devices.enums.Status;
import com.damintsev.client.service.Service;
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

    private static Long id;
    private static UICenterField instance;

    public static UICenterField get() {
        if(instance == null) instance = new UICenterField();
        return instance;
    }

    private PickupDragController dragController;
    private AbsolutePanel panel;
    private Map<UIItem<Station>, ArrayList<UIItem<? extends Device>>> uiIems;

    private UICenterField() {
        uiIems = new HashMap<UIItem<Station>, ArrayList<UIItem<? extends Device>>>();
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

    public void addItem(UIItem<? extends Device> item) {
        addItem(item, true);
    }

    public void addItem(UIItem<? extends Device> item, boolean newI) {   //todo Добавить двойной клик на элемент
        if(item.getId()==null)item.setId(getNextId());
        if(item.getType()==DeviceType.STATION) {
            System.out.println("addStation id=" + item.getId());
                uiIems.put((UIItem<Station>) item, new ArrayList<UIItem<? extends Device>>());
        } else {
            System.out.println("addDevice id=" + item.getId());
            Station station = item.getStation();
            System.out.println("CPT =" + uiIems.get(getUIIStationItem(station)));
            uiIems.get(getUIIStationItem(station)).add(item);
        }
        dragController.makeDraggable(item);
        panel.add(item, 0, 0);
        if (newI) {
            int centerX = Window.getClientWidth() / 2 - item.getWidth() / 2;
            int centerY = Window.getClientHeight() / 2 - item.getHeight() / 2;
            panel.setWidgetPosition(item, centerX, centerY);
        } else {
            Position pos = item.getPosition();
            panel.setWidgetPosition(item, pos.x, pos.y);
        }
        drawConnections(false);
    }

    private void allowDrag(){
        for(Map.Entry<UIItem<Station>, ArrayList<UIItem<? extends Device>>> entry : uiIems.entrySet()) {
            UIItem<Station> station = entry.getKey();
            dragController.makeDraggable(station);
            for(UIItem item : entry.getValue()) {
                dragController.makeDraggable(item);
            }
        }
    }

    private void disAllowDrag() {
        for(Map.Entry<UIItem<Station>, ArrayList<UIItem<? extends Device>>> entry : uiIems.entrySet()) {
            UIItem<Station> station = entry.getKey();
            dragController.makeNotDraggable(station);
            for(UIItem item : entry.getValue()) {
                dragController.makeNotDraggable(item);
            }
        }
    }

    public void saveItemPositions() {
        dragController.clearSelection();
        disAllowDrag();
        for(Map.Entry<UIItem<Station>, ArrayList<UIItem<? extends Device>>> entry : uiIems.entrySet()) {
            entry.getKey().savePosition();
            for(UIItem item : entry.getValue()) {
                item.savePosition();
            }
        }
        drawConnections(false);
        saveToDatabase();
        start();
        schedule();
    }
    
    public void saveToDatabase() {
        List<Item> items = new ArrayList<Item>();
        for(Map.Entry<UIItem<Station>, ArrayList<UIItem<? extends Device>>> entry : uiIems.entrySet()) {
            items.add(entry.getKey().getItem());
            for(UIItem item : entry.getValue()) {
                items.add(item.getItem());
            }
        }
        Service.instance.saveItems(items, new AsyncCallback<Boolean>() {
            public void onFailure(Throwable throwable) {
                Dialogs.alert(throwable.getMessage());
            }

            public void onSuccess(Boolean bool) {
            }
        });
    }

    public Widget getSelected() {
        Iterator<Widget> it = dragController.getSelectedWidgets().iterator();
        return it.hasNext() ? it.next() : null;
    }

    public void delete(Device device) {
        if(device instanceof Station) {
            UIItem station = getUIIStationItem((Station) device);
            ArrayList<UIItem<? extends Device>> devices = uiIems.get(station);
            for(UIItem<? extends Device> uiDevice : devices)  {
                deleteDevice(uiDevice);
            }
            deleteStation(station);
        } else {
            deleteDevice(getUIIDeviceItem(device));
        }
        drawConnections(false);
    }

    private void deleteDevice(UIItem device){
        dragController.makeNotDraggable(device);
        panel.remove(device);
        uiIems.get(getUIIStationItem(device.getStation())).remove(device);
    }

    private void deleteStation(UIItem station) {
        dragController.makeNotDraggable(station);
        panel.remove(station);
        uiIems.remove(station);
    }
    
    private void loadFromDatabase() {
        System.out.println("load from database!");
        Service.instance.loadItems(new AsyncCallback<List<Item>>() {
            public void onFailure(Throwable throwable) {
                Dialogs.alert("Error loading from database! " + throwable.getMessage());
            }

            public void onSuccess(List<Item> items) {
                System.out.println("loaded " + items.size());
                uiIems.clear();
                Long id = -1L;
                for(Item item : items) {
                    System.out.println("loading from db id=" + item.getId());
                    addItem(new UIItem(item), false);
                  if(id < item.getId()) {
                      id = item.getId();
                  }
                }
                if (id == -1L) {
                    UICenterField.id = 0L;
                } else
                    UICenterField.id = id;
                if(items.size() != 0) {
                    start();
                    schedule();
                    disAllowDrag();
                }
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
        for(Map.Entry<UIItem<Station>, ArrayList<UIItem<? extends Device>>> entry : uiIems.entrySet()) {
            UIItem<? extends Device> station = entry.getKey();
            station.setLabelColor();
            for(UIItem item : entry.getValue()) {
                drawLine(station.getCenterPosition(), item.getCenterPosition(), item.getStatus());
            }
        }
    }

    public void drawLine(Position from, Position to, Status status) {
        if(from.x == to.x && from.y == to.y) return;
        Context2d context = canvas.getContext2d();
        context.beginPath();
        context.setLineWidth(3);
        context.setStrokeStyle(status.getColor());
        context.moveTo(from.x, from.y);
        context.lineTo(to.x, to.y);
        context.stroke();
    }

    private void clearCanvas() {
        Context2d context = canvas.getContext2d();
        context.clearRect(0, 0, Window.getClientWidth(), Window.getClientHeight());
    }

    private Long getNextId() {
        if(id == null) id = 0L;
        return id++;
    }
    
    public List<Station> getStations() {
        List<Station> list = new ArrayList<Station>(); //todo change array list
        for(UIItem<Station> item :uiIems.keySet()){
            list.add(item.getItem().getData());
        }
        return list;
    }

    public UIItem<Station> getUIIStationItem(Station station) {
        for(UIItem<Station> uiStations : uiIems.keySet()) {
            if(uiStations.getId().equals(station.getId()))
                return uiStations;
        }
        return null;
    }
    public UIItem getUIIDeviceItem(Device station) {
        for(Map.Entry<UIItem<Station>, ArrayList<UIItem<? extends Device>>> entry : uiIems.entrySet()) {
            for(UIItem item : entry.getValue()) {
                if(item.getId().equals(station.getId()))
                    return item;
            }
        }
        return null;
    }

    public void schedule() {
        Scheduler.get().scheduleFixedPeriod(new Scheduler.RepeatingCommand() {
            public boolean execute() {
                scheduler();
                return start;
            }
        },1000);
    }

    private void scheduler() {
        if(iterator == null)
            iterator = createIterator();
        if(iterator.hasNext()) {
            Service.instance.checkDevice(iterator.next().getData(), new AsyncCallback<Device>() {
                public void onFailure(Throwable caught) {
                    stop();
                    Dialogs.alert("Check device false! " + caught.getMessage());   //todo!!!!
                }

                public void onSuccess(Device result) {
                    if (result != null) {
                        if (result.getDeviceType() == DeviceType.STATION) {

                        } else {
                            ArrayList<UIItem<? extends Device>> items = uiIems.get(getUIIStationItem(result.getStation()));
                            UIItem item = getUIIDeviceItem(result);
                            if(item== null) return;
//                            System.out.println("r.gS" + result.getStation());
//                            System.out.println("uiI.g" + uiIems.get(result.getStation()));
                            uiIems.get(getUIIStationItem(result.getStation())).remove(item);
                            item.getItem().getData().setStatus(result.getStatus());
                            items.add(item);
                            drawConnections(false);
                        }
                    }
                }
            });
        } else {
           iterator = createIterator();
        }
    }
    private Iterator<Item> iterator;

    private Iterator<Item> createIterator() {
        List<Item> items = new ArrayList<Item>();
        for(Map.Entry<UIItem<Station>, ArrayList<UIItem<? extends Device>>> entry : uiIems.entrySet()) {
            items.add(entry.getKey().getItem());
            for(UIItem item : entry.getValue()) {
                items.add(item.getItem());
            }
        }
        return items.iterator();
    }

    private boolean start;

    public void start() {
        this.start = true;
    }

    public void stop() {
        this.start = false;
    }
}