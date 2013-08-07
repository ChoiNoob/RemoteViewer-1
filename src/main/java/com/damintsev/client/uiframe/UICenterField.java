package com.damintsev.client.uiframe;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.drop.AbsolutePositionDropController;
import com.allen_sauer.gwt.dnd.client.drop.DropController;
import com.damintsev.utils.Dialogs;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.box.MessageBox;
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
    private AbsolutePanel panel;
    private List<UIItem> items = new ArrayList<UIItem>();
    private UIItem mainItem;

    private UICenterField() {
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

    public Canvas getCanvas() {
        return canvas;
    }

    public Widget getContent() {
        return panel;
    }

    public void addItem(UIItem item) {
        item.init();
        if(item.getType() == ItemType.STATION) {
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
        items.add(item);
        drawConnections(false);
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
            if(position != null) panel.setWidgetPosition(item, position.x, position.y);
            else item.savePosition();
        }
        drawConnections(false);
    }

    private void drawConnections(boolean fireEvent) {
        if(mainItem == null) {
            Dialogs.alert("В системе нет головной станции");
        }
        clearCanvas();
        for(UIItem item : items) {
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
}