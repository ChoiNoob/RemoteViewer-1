package com.damintsev.client.uiframe;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.drop.AbsolutePositionDropController;
import com.allen_sauer.gwt.dnd.client.drop.DropController;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

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
    private List<UIItem> items;

    public Widget getContent() {
        panel = new AbsolutePanel();
        dragController = new PickupDragController(panel, true);
        dragController.setBehaviorConstrainedToBoundaryPanel(false);
        DropController dropController = new AbsolutePositionDropController(panel);
        dragController.registerDropController(dropController);
//        for (int i = 1; i <= 5; i++) {
//            // create a label and give it style
//            Label label = new Label("Label #" + i, false);
//            label.setPixelSize(150,150);
//            dragController.makeDraggable(label);
//            panel.add(label);
//        }

        return panel;
    }

    public void addItem(UIItem item) {
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
}
