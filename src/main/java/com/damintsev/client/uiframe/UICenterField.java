package com.damintsev.client.uiframe;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.drop.AbsolutePositionDropController;
import com.allen_sauer.gwt.dnd.client.drop.DropController;
import com.damintsev.client.dao.DeviceType;
import com.damintsev.client.dao.Item;
import com.damintsev.client.service.Service;
import com.damintsev.utils.Dialogs;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Damintsev Andrey
 * Date: 01.08.13
 * Time: 23:17
 */
public class UICenterField {

    private static int id = 0;
    private static UICenterField instance;

    public static UICenterField get() {
        if(instance == null) instance = new UICenterField();
        return instance;
    }

    private PickupDragController dragController;
    private AbsolutePanel panel;
    private List<UIItem> uiItems;
    private UIItem mainItem;

    private UICenterField() {
        System.out.println("UICenterField construct");
        uiItems = new ArrayList<UIItem>();
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
        dragController.setBehaviorMultipleSelection(false);

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
        item.setId(getNextId());
        if(item.getType() == DeviceType.STATION) {
            if(mainItem == null)
                mainItem = item;
            else
                Dialogs.alert("В система уже есть головная станция");
        }
        dragController.makeDraggable(item);
        panel.add(item, 0, 0);
        int centerX = Window.getClientWidth() / 2 - item.getWigth() / 2;
        int centerY = Window.getClientHeight() / 2 - item.getHeight() / 2;
        panel.setWidgetPosition(item, centerX, centerY);
        uiItems.add(item);
        drawConnections(false);
    }

    private void allowDrag(){
        for(UIItem item : uiItems) {
            dragController.makeDraggable(item);
        }
    }

    private void disAllowDrag() {
        for(UIItem item : uiItems) {
            dragController.makeNotDraggable(item);
        }
    }

    public void saveItemPositions() {
        if(mainItem == null) Dialogs.alert("В системе нет головной станции");
        disAllowDrag();
        for(UIItem item : uiItems)
            item.savePosition();
        drawConnections(false);
        saveToDatabase();
    }
    
    public void saveToDatabase() {
        List<Item> items = new ArrayList<Item>(uiItems.size());
        for(UIItem ui : uiItems) {
            items.add(ui.getItem());
        }
        Service.instance.saveItems(items, new AsyncCallback<Boolean>() {
            public void onFailure(Throwable throwable) {
                Dialogs.alert(throwable.getMessage());
            }

            public void onSuccess(Boolean bool) {
            }
        });
    }
    
    private void loadFromDatabase() {
        Service.instance.loadItems(new AsyncCallback<List<Item>>() {
            public void onFailure(Throwable throwable) {
                Dialogs.alert(throwable.getMessage());
            }

            public void onSuccess(List<Item> items) {
                System.out.println("loaded " + items.size());
                int tmp = -1;
                for(Item item : items) {
                    uiItems.add(new UIItem(item));
                    if (item.getId() > tmp)
                        tmp = item.getId();
                }
                id = tmp >= 0 ? tmp : 0;
                rasstavitItems();
            }
        });
    }

    public void revertItemPositions() {
        disAllowDrag();
        rasstavitItems();
        drawConnections(false);
    }

    private void rasstavitItems() {
        for (UIItem item : uiItems) {
            UIItem.Position position = item.getPosition();
            panel.add(item,0,0);
            if(position != null) panel.setWidgetPosition(item, position.x, position.y);
            else item.savePosition();
        }
        drawConnections(false);
    }

    private void drawConnections(boolean fireEvent) {
        if(mainItem == null) return;
        clearCanvas();
        for(UIItem item : uiItems) {
            drawLine(mainItem.getCenterPosition(), item.getCenterPosition(), fireEvent);
        }
    }

    public void drawLine(UIItem.Position from, UIItem.Position to, boolean fire) {
        if(from.x == to.x && from.y == to.y) return;

        Context2d context = canvas.getContext2d();
        context.beginPath();
        context.setLineWidth(3);
        context.moveTo(from.x, from.y);
        context.lineTo(to.x, to.y);
        context.stroke();
    }

    public void clearCanvas() {
        Context2d context = canvas.getContext2d();
        context.clearRect(0,0,Window.getClientWidth(),Window.getClientHeight());
    }

    private int getNextId() {
        return id++;
    }
}