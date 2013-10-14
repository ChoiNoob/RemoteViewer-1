package com.damintsev.client.v3.pages.frames;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.drop.AbsolutePositionDropController;
import com.allen_sauer.gwt.dnd.client.drop.DropController;
import com.damintsev.client.devices.Item;
import com.damintsev.client.devices.UIItem;
import com.damintsev.client.service.Service2;
import com.damintsev.client.v3.items.task.TaskState;
import com.damintsev.client.v3.utilities.DataLoader;
import com.damintsev.client.v3.utilities.Scheduler;
import com.damintsev.utils.Position;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.Command;
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

        final TextButton editButton = new TextButton("Редактировать", new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent event) {
                startEditing();
                SettingsFrame.get().expand();
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

    private void addItem(Item item) {
        System.out.println("add item");
        UIItem uiItem = new UIItem(item);
        uiItems.put(uiItem.getId(), uiItem);
        panel.add(uiItem, 0, 0);
        panel.setWidgetPosition(uiItem, uiItem.getPosition().x, uiItem.getPosition().y);
        if(editing) dragController.makeDraggable(uiItem);
        drawConnections();
    }

    private void startEditing() {
        stop();
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
            item.savePosition();
        }
        saveItemPositions();
        start();
    }

    public void saveItemPositions() {
        DataLoader.getInstance().saveUIItems(uiItems.values());
    }

    public Widget getSelected() {
        Iterator<Widget> it = dragController.getSelectedWidgets().iterator();
        return it.hasNext() ? it.next() : null;
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
        System.out.println("CCCCCCCCCCCC=" + uiItems.get(null));
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
//                System.out.println("Call loadTaskStates date=" + new Date());
                Service2.database.loadTaskStates(new AsyncCallback<List<TaskState>>() {
                    public void onFailure(Throwable caught) {
                        //todo грамотно обраотать ошибку!
                    }

                    public void onSuccess(List<TaskState> result) {
                        System.out.println("res=" + result.size());
                        for (TaskState state : result) {
                            System.out.println("state id=" + state.getId()  + " state="+ state.getState());
                            if (uiItems.get(state.getId()) != null) {
                                uiItems.get(state.getId()).setTaskState(state);}
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