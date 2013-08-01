package com.damintsev.client.uiframe;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.drop.AbsolutePositionDropController;
import com.allen_sauer.gwt.dnd.client.drop.DropController;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

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

    public Widget getContent() {
        AbsolutePanel panel = new AbsolutePanel();
        PickupDragController dragController = new PickupDragController(panel, true);
        dragController.setBehaviorConstrainedToBoundaryPanel(false);
        DropController dropController = new AbsolutePositionDropController(panel);
        dragController.registerDropController(dropController);
        for (int i = 1; i <= 5; i++) {
            // create a label and give it style
            Label label = new Label("Label #" + i, false);
            label.setPixelSize(150,150);
            dragController.makeDraggable(label);
            panel.add(label);
        }

        return panel;
    }
}
