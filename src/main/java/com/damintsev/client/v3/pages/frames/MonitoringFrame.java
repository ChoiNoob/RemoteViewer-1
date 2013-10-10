package com.damintsev.client.v3.pages.frames;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.drop.AbsolutePositionDropController;
import com.allen_sauer.gwt.dnd.client.drop.DropController;
import com.damintsev.client.devices.Device;
import com.damintsev.client.devices.Item;
import com.damintsev.client.devices.UIItem;
import com.damintsev.client.devices.enums.Status;
import com.damintsev.client.service.Service2;
import com.damintsev.client.uiframe.UISettingsPanel;
import com.damintsev.client.v3.items.task.TaskState;
import com.damintsev.client.v3.utilities.DataLoader;
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
public class MonitoringFrame {

    private static MonitoringFrame instance;

    public static MonitoringFrame get() {
        if (instance == null) instance = new MonitoringFrame();
        return instance;
    }

    private PickupDragController dragController;
    private AbsolutePanel panel;
    private boolean editing = false;
//    private Map<Long, UIItem> uiDevices;
    private Map<String, UIItem> uiItems;

    private MonitoringFrame() {
        uiItems = new HashMap<String, UIItem>();
//        uiStations = new HashMap<Long, UIItem>();
//        uiDevices = new HashMap<Long, UIItem>();
        panel = new AbsolutePanel();
        dragController = new PickupDragController(panel, true) {
            @Override
            public void dragEnd() {
                drawConnections();
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
                startEditing();
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
        DataLoader.getInstance().load();
        drawConnections();
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

    //todo really need update ?!
    public void addItem(Item item) {
        UIItem uiItem = new UIItem(item);
        uiItems.put(uiItem.getId(), uiItem);
        panel.add(uiItem, 0, 0);
        panel.setWidgetPosition(uiItem, uiItem.getPosition().x, uiItem.getPosition().y);
        if(editing) dragController.makeDraggable(uiItem);
    }

    private void startEditing() {
        editing = true;
        for(UIItem item : uiItems.values()) {
            dragController.makeDraggable(item);
        }
    }

    public void stopEditing() {
        editing = false;
        dragController.clearSelection();
        for(UIItem item : uiItems.values()) {
            dragController.makeNotDraggable(item);
        }
    }

    public void saveItemPositions() {
        DataLoader.getInstance().saveUIItems(uiItems.values());
    }

    public Widget getSelected() {
        Iterator<Widget> it = dragController.getSelectedWidgets().iterator();
        return it.hasNext() ? it.next() : null;
    }

    public void delete(UIItem item) {
        if(editing) dragController.makeNotDraggable(item);
        panel.remove(item);
        if(item.haveChildrens()) {
            for(UIItem child : uiItems.values())
                if(item.isChild(child))
                    delete(child);
        }
    }

    private void drawConnections() {
        clearCanvas();
        for(UIItem uiItem : uiItems.values()) {
            UIItem parent = uiItems.get(uiItem.getParentId());
            if(parent != null)
                drawLine(parent.getCenterPosition(), uiItem.getCenterPosition(), uiItem.getTaskState());
        }
    }

    public void drawLine(Position from, Position to, TaskState status) {
        if (from.x == to.x && from.y == to.y) return;
        Context2d context = canvas.getContext2d();
        context.beginPath();
        context.setLineWidth(3);
        System.out.println("draw s= " + status + " color=" + status.getState().getColor());
        context.setStrokeStyle(status.getState().getColor());
        context.moveTo(from.x, from.y);
        context.lineTo(to.x, to.y);
        context.stroke();
    }

    private void clearCanvas() {
        Context2d context = canvas.getContext2d();
        context.clearRect(0, 0, Window.getClientWidth(), Window.getClientHeight());
    }

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

    public static native void reload()/*-{
        $wnd.location = $wnd.location.pathname;
    }-*/;
}