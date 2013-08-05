package com.damintsev.client.uiframe;

import com.damintsev.client.dao.Item;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

/**
 * User: Damintsev Andrey
 * Date: 03.08.13
 * Time: 2:09
 */
public abstract class UIItem extends Image {

    private Item item;
    private Position position;

    public void savePosition() {
        if(position == null) position = new Position(getAbsoluteLeft(), getAbsoluteTop());
        else {
            position.x = getAbsoluteLeft();
            position.y = getAbsoluteTop();
        }
    }

    public Position getPosition() {
        return position;
    }

    public abstract String getName();

//    @Override
//    public abstract Widget asWidget();

    public void setItem(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    class Position {

        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        int x;
        int y;
    }
}
