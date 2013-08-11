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
            }
        });
        editButton.setAllowTextSelection(false);
        editButton.getElement().getStyle().setTop(5, Style.Unit.PX);
        editButton.getElement().getStyle().setRight(5, Style.Unit.PX);
        editButton.getElement().getStyle().setPosition(Style.Position.ABSOLUTE);

        panel.add(editButton);
        drawCanvas(panel);
//        loadFromDatabase();
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

    public void addItem(UIItem<? extends Device> item) {   //todo Добавить двойной клик на элемент
        item.setId(getNextId());
        if(item.getType()==DeviceType.STATION) {
                uiIems.put((UIItem<Station>) item, new ArrayList<UIItem<? extends Device>>());
        } else {
            Station station = item.getStation();
            uiIems.get(getUIItem(station)).add(item);
        }
        dragController.makeDraggable(item);
        panel.add(item, 0, 0);
        int centerX = Window.getClientWidth() / 2 - item.getWidth() / 2;
        int centerY = Window.getClientHeight() / 2 - item.getHeight() / 2;
        panel.setWidgetPosition(item, centerX, centerY);
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
            UIItem station = getUIItem((Station) device);
            ArrayList<UIItem<? extends Device>> devices =uiIems.get(station);
            for(UIItem<? extends Device> uiDevice : devices)  {
                deleteDevice(uiDevice);
            }
            deleteStation(station);
        } else {

        }
    }

    private void deleteDevice(UIItem device){
        dragController.makeNotDraggable(device);
        panel.remove(device);
        uiIems.get(device.getStation()).remove(device);
    }

    private void deleteStation(UIItem station) {
        dragController.makeNotDraggable(station);
        panel.remove(station);
        uiIems.remove(station);
    }
    
//    private void loadFromDatabase() {
//        Service.instance.loadItems(new AsyncCallback<List<Item>>() {
//            public void onFailure(Throwable throwable) {
//                Dialogs.alert(throwable.getMessage());
//            }
//
//            public void onSuccess(List<Item> items) {
//                System.out.println("loaded " + items.size());
//                int tmp = -1;
//                for(Item item : items) {
//                    uiItems.add(new UIItem(item));
//                    if (item.getId() > tmp)
//                        tmp = item.getId();
//                }
//                id = tmp >= 0 ? tmp : 0;
//                rasstavitItems();
//            }
//        });
//    }

    public void revertItemPositions() {
        dragController.clearSelection();
        disAllowDrag();
//        rasstavitItems();
        drawConnections(false);
    }

//    private void rasstavitItems() {
//        for (UIItem item : uiItems) {
//            Position position = item.getPosition();
//            panel.add(item,0,0);
//            if(position != null) panel.setWidgetPosition(item, position.x, position.y);
//            else item.savePosition();
//        }
//        drawConnections(false);
//    }

    private void drawConnections(boolean fireEvent) {
        clearCanvas();
        for(Map.Entry<UIItem<Station>, ArrayList<UIItem<? extends Device>>> entry : uiIems.entrySet()) {
            UIItem<? extends Device> station = entry.getKey();
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
        if(id == null) id = 0L; //todo
        return id++;
    }
    
    public List<Station> getStations() {
        List<Station> list = new ArrayList<Station>(); //todo change array list
        for(UIItem<Station> item :uiIems.keySet()){
            list.add(item.getItem().getData());
        }
        return list;
    }

    public UIItem<Station> getUIItem(Station station) {
        for(UIItem<Station> uiStations : uiIems.keySet()) {
            if(uiStations.getId().equals(station.getId()))
                return uiStations;
        }
        return null;
    }
}