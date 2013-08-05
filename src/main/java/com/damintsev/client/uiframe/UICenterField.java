package com.damintsev.client.uiframe;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.drop.AbsolutePositionDropController;
import com.allen_sauer.gwt.dnd.client.drop.DropController;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
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

    private static UICenterField instance;

    public static UICenterField get() {
        if(instance == null) instance = new UICenterField();
        return instance;
    }

    private PickupDragController dragController;
    AbsolutePanel panel;
    private List<UIItem> items = new ArrayList<UIItem>();
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
            }
        });
        editButton.setAllowTextSelection(false);
        editButton.getElement().getStyle().setTop(5, Style.Unit.PX);
        editButton.getElement().getStyle().setRight(5, Style.Unit.PX);
        editButton.getElement().getStyle().setPosition(Style.Position.ABSOLUTE);

        panel.add(editButton);
    }

    public Widget getContent() {
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

    public void saveItemPositions() {
        disAllowDrag();
        for(UIItem item : items) {
            item.savePosition();
        }
    }

    public void revertItemPositions() {
        disAllowDrag();
        for (UIItem item : items) {
            UIItem.Position position = item.getPosition();
            panel.setWidgetPosition(item, position.x, position.y);
        }
    }
}
