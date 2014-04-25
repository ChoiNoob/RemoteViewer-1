package com.damintsev.client.v3.pages.frames;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.drop.AbsolutePositionDropController;
import com.allen_sauer.gwt.dnd.client.drop.DropController;
import com.damintsev.client.EventBus;
import com.damintsev.client.old.devices.Item;
import com.damintsev.client.service.Service;
import com.damintsev.client.v3.uiitems.UIItem;
import com.damintsev.client.v3.utilities.DataLoader;
import com.damintsev.client.v3.utilities.Scheduler;
import com.damintsev.common.event.*;
import com.damintsev.common.uientity.ExecuteState;
import com.damintsev.common.uientity.TaskState;
import com.damintsev.common.utils.Position;
import com.damintsev.common.visitor.UIVisitor;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Widget;

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
    private Map<String, UIItem> uiItems;
    private Canvas canvas;

    private MonitoringFrame() {
        uiItems = new HashMap<String, UIItem>();
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
        EventBus.get().addHandler(StartEditEvent.TYPE, new StartEditEventHandler() {
            @Override
            public void onStartEdit(StartEditEvent event) {
                if(!editing) {
                    startEditing();
                    SettingsFrame.get().expand();
                    stop();
                }
            }
        });
        EventBus.get().addHandler(StopEditEvent.TYPE, new StopEditEventHandler() {
            @Override
            public void onStopEdit(StopEditEvent event) {
                if(editing)
                    stopEditing();
            }
        });
        drawCanvas(panel);
//        panel.addHandler(new ResizeHandler() {
//            @Override
//            public void onResize(ResizeEvent event) {
//                Dialogs.alert("fuck");
//            }
//        }, ResizeEvent.getType());
        DataLoader.getInstance().load();
//        drawConnections();
    }

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


    public void add(Item item) {
        if(uiItems.containsKey(item.getStringId()))
            updateItem(item);
        else
            addItem(item);
    }

    private void updateItem(Item item) {
        System.out.println("update item");
        UIItem uiItem = uiItems.get(item.getStringId());
        uiItem.setItem(item);

        //todo need redraw! may be force
    }

    private UIVisitor visitor = new UIVisitor();

    private void addItem(Item item) {
        UIItem uiItem = item.accept(visitor);
        uiItems.put(uiItem.getId(), uiItem);
        panel.add(uiItem, 0, 0);
        panel.setWidgetPosition(uiItem, uiItem.getPosition().x, uiItem.getPosition().y);
        if (editing) dragController.makeDraggable(uiItem);
        drawConnections();
        saveItemPositions(uiItem);
    }

    private void startEditing() {
        stop();
        editing = true;
        for(UIItem item : uiItems.values()) {
            dragController.makeDraggable(item);
        }
//        UITest test = new UITest();
//        panel.add(test, 0, 0);
//        panel.setWidgetPosition(test, 150, 150);
    }


    public void stopEditing() {
        editing = false;
        dragController.clearSelection();
        for(UIItem item : uiItems.values()) {
            dragController.makeNotDraggable(item);
            item.savePosition();
        }
        saveItemPositions();
        start();
    }

    public void saveItemPositions() {
        DataLoader.getInstance().saveUIItems(uiItems.values());
    }

    public void saveItemPositions(UIItem item) {
        DataLoader.getInstance().saveUIItems(Arrays.asList(item));
    }

    public UIItem getSelected() {
        Iterator<Widget> it = dragController.getSelectedWidgets().iterator();
        return it.hasNext() ? (UIItem)it.next() : null;
    }

    public void delete(UIItem item) {
//        uiItems.remove(item.getId());     todo продуиать нормальное удаление!
//        if(editing) dragController.makeNotDraggable(item);
//        panel.remove(item);
//        System.out.println("CPTTT" + item.getId());
//        if(item.haveChildrens()) {
////todo            List<UIItem> childrensToDelete
//            for(UIItem child : uiItems.values())
//                if(item.isChild(child))
//                    delete(child);
//        }
    }

    public void delete(Item item) {
        delete(uiItems.get(item.getStringId()));
    }

    private void drawConnections() {
        clearCanvas();
        for(UIItem uiItem : uiItems.values()) {
            UIItem parent = uiItems.get(uiItem.getParentId());
            System.out.println("id=" + uiItem.getId() + " parent=" + uiItem.getParentId());
            if(parent != null)
                drawLine(parent.getCenterPosition(), uiItem.getCenterPosition(), uiItem.getTaskState());
        }
    }

    public void drawLine(Position from, Position to, TaskState status) {
        if (from.x == to.x && from.y == to.y) return;
        Context2d context = canvas.getContext2d();
        context.beginPath();
        context.setLineWidth(3);
//        System.out.println("draw s= " + status + " color=" + status.getState().getColor());
        context.setStrokeStyle(status.getState().getColor());
        context.moveTo(from.x, from.y);
        context.lineTo(to.x, to.y);
        context.stroke();
    }

    private void clearCanvas() {
        Context2d context = canvas.getContext2d();
        context.clearRect(0, 0, Window.getClientWidth(), Window.getClientHeight());
    }

    public void start() {
        Scheduler.getInstance().start(this.getClass(), new Runnable() {
            public void run() {
                Service.instance.loadTaskStates(new AsyncCallback<List<TaskState>>() {
                    public void onFailure(Throwable caught) {
                        //todo грамотно обраотать ошибку!
                    }

                    public void onSuccess(List<TaskState> result) {
                        System.out.println("res=" + result.size());
                        for (TaskState state : result) {
                            System.out.println("state id=" + state.getId()  + " state="+ state.getState());
                            if (uiItems.get(state.getId()) != null) {
                                uiItems.get(state.getId()).setTaskState(state);
                                if(state.getState() == ExecuteState.ERROR) {
                                    EventBus.get().fireEvent(new AlarmEvent(state, uiItems.get(state.getId()).getItem()));
                                }
                            }
                            else {
                                int i = 0; //todo!!!!
                            }
                        }
                        drawConnections();
                    }
                });
            }
        });
    }

    public void stop() {
        Scheduler.getInstance().stop(this.getClass());
    }

    public void reloadView() {
        stop();
//        for(UIItem uiItem : uiItems.values())
//            delete(uiItem);
//        DataLoader.getInstance().load();
//        start();
        com.google.gwt.core.client.Scheduler.get().scheduleDeferred(new Command() {
            public void execute() {
                reload();
            }
        });
    }

    public static native void reload()/*-{
        $wnd.location = $wnd.location.pathname;
    }-*/;
}