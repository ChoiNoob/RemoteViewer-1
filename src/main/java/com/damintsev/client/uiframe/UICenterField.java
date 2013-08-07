package com.damintsev.client.uiframe;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.drop.AbsolutePositionDropController;
import com.allen_sauer.gwt.dnd.client.drop.DropController;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
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

    private static UICenterField instance;

    public static UICenterField get() {
        if(instance == null) instance = new UICenterField();
        return instance;
    }

    private PickupDragController dragController;
    AbsolutePanel panel;
    private List<UIItem> items = new ArrayList<UIItem>();
    private UIItem mainItem;
    private boolean editMode = false;

    private UICenterField() {
        panel = new AbsolutePanel();
        dragController = new PickupDragController(panel, true);
        dragController.setBehaviorConstrainedToBoundaryPanel(false);
        DropController dropController = new AbsolutePositionDropController(panel);
        dragController.registerDropController(dropController);
        dragController.setBehaviorMultipleSelection(false);

        final TextButton editButton = new TextButton("Редактировать", new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent event) {
                allowDrag();
                UISettingsPanel.get().expand();
                editMode = true;
                canvasDemo(null,null);
            }
        });
        editButton.setAllowTextSelection(false);
        editButton.getElement().getStyle().setTop(5, Style.Unit.PX);
        editButton.getElement().getStyle().setRight(5, Style.Unit.PX);
        editButton.getElement().getStyle().setPosition(Style.Position.ABSOLUTE);

        panel.add(editButton);

//        HTML canvas = new HTML("<canvas id=\"myCanvas\" width=\"" + Window.getClientWidth() +
//                "\" height=\"" + Window.getClientHeight() +
//                "\"></canvas>");
//        panel.getElement().appendChild(canvas.getElement());
    }

    public Widget getContent() {
        return panel;
    }

    public void addItem(UIItem item) {
        item.init();
        if(item.getType() == ItemType.STATION) {
            mainItem = item;
        }
        dragController.makeDraggable(item);
        panel.add(item, 0, 0);
        panel.setWidgetPosition(item, Window.getClientWidth() / 2, Window.getClientHeight() / 2);
        if(items == null) items = new ArrayList<UIItem>();
        items.add(item);
    }

    public void allowDrag(){
        for(UIItem item : items) {
            dragController.makeDraggable(item);
        }
    }

    public void disAllowDrag() {
        for(UIItem item : items) {
            dragController.makeNotDraggable(item);
        }
    }

    public void saveItemPositions() {
        disAllowDrag();
        for(UIItem item : items) {
            item.savePosition();
        }
        drawConnections(false);
    }

    public void revertItemPositions() {
        disAllowDrag();
        for (UIItem item : items) {
            UIItem.Position position = item.getPosition();
            if(position != null)
                panel.setWidgetPosition(item, position.x, position.y);
            else
                item.savePosition();
        }
        drawConnections(false);
    }

    private void drawConnections(boolean fireEvent) {
//        if (mainItem == null) {
//            for (UIItem item : items) {
//                if (item.getType() == ItemType.STATION) {
//                    mainItem = item;
//                    break;
//                }
//            }
//        }
        for(UIItem item : items) {
            drawLine(mainItem.getCenterPosition(), item.getCenterPosition(), fireEvent);
        }

    }

    public void drawLine(UIItem.Position from, UIItem.Position to, boolean fire) {
//        drawLine(from.x, from.y, to.x, to.y, fire);
        canvasDemo(from, to);
    }

    public void  canvasDemo(UIItem.Position from, UIItem.Position to) {
//        System.out.println("fromX=" + from.x + " fromY=" + from.y + " toX=" + to.x + " toY=" + to.y);
//        if(from.x == to.x && from.y == to.y)
//            return;
        Canvas canvas = Canvas.createIfSupported();
        canvas.setPixelSize(400,400);
         canvas.getElement().getStyle().setZIndex(10000);
        panel.add(canvas);
        Context2d context = canvas.getContext2d();

        context.beginPath();
        context.setLineWidth(3);
        context.moveTo(0, 0);
        context.lineTo(100, 100);
//        context.moveTo(100,100);
        context.lineTo(200, 0);
//        context.lineTo(25,40);
//        context.lineTo(25,0);
//        context.fill();
        context.stroke();
//        context.closePath();

    }
}