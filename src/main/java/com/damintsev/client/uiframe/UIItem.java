package com.damintsev.client.uiframe;

import com.damintsev.client.Utils;
import com.damintsev.client.dao.Item;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.*;
import com.sencha.gxt.fx.client.Draggable;
import com.sencha.gxt.widget.core.client.ContentPanel;

/**
 * User: Damintsev Andrey
 * Date: 03.08.13
 * Time: 2:09
 */
public class UIItem extends Label {

    private String name;
    private Item item;
    private Position position;
    private Position centerPosition;
    private ImageResource image;
    private ItemType type;

    public UIItem() {
        super();
    }

    public void savePosition() {
        if(position == null) position = new Position(getAbsoluteLeft(), getAbsoluteTop());
        else {
            position.x = getAbsoluteLeft();
            position.y = getAbsoluteTop();
        }
    }

    public void init() {
        setHorizontalAlignment(ALIGN_CENTER);
        Label label = new Label(getName());
        if(getName() != null)
            getElement().appendChild(label.getElement());
        label.setStyleName("tooltip");
        getElement().appendChild(new Image(image).getElement());
    }

    public Position getCenterPosition() {
        int x = getAbsoluteLeft() + image.getWidth() / 2;
        int y = getAbsoluteTop() + image.getHeight() / 2;
        centerPosition = new Position(x,y);
        return centerPosition;
    }

    public Position getPosition() {
        return position;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public ImageResource getImage() {
        return image;
    }

    public void setImage(ImageResource image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }
    
    public int getWigth() {
        return image.getWidth();
    }

    public int getHeight() {
        return image.getHeight();
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
