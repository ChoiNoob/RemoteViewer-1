package com.damintsev.client.uiframe;

import com.damintsev.client.Utils;
import com.damintsev.client.dao.DeviceType;
import com.damintsev.client.dao.Item;
import com.damintsev.client.dao.MyInter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.*;
import com.sencha.gxt.fx.client.Draggable;
import com.sencha.gxt.widget.core.client.ContentPanel;

import java.io.Serializable;

/**
 * User: Damintsev Andrey
 * Date: 03.08.13
 * Time: 2:09
 */
public class UIItem extends Label {

    private Item item;
    private Position position;
    private Position centerPosition;
    private Image image;

    public UIItem(MyInter data) {
        super();
        item = new Item();
        this.item.setData(data);
        init();
    }

    public UIItem(Item item) {
        super();
        this.item = item;
        position = new Position(item.getCoordX(), item.getCoordY());
        init();
    }

    public void savePosition() {
        if(position == null) position = new Position(getAbsoluteLeft(), getAbsoluteTop());
        else {
            position.x = getAbsoluteLeft();
            position.y = getAbsoluteTop();
        }
    }

    private void init() {
        setHorizontalAlignment(ALIGN_CENTER);
        Label label = new Label(getName());
        if(getName() != null)
            getElement().appendChild(label.getElement());
        label.setStyleName("tooltip");
        image = new Image(getImage());
        getElement().appendChild(image.getElement());
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

//    public void setItem(Item item) {
//        this.item = item;
//    }

    public Item getItem() {
        item.setCoordX(getPosition().x);
        item.setCoordY(getPosition().y);
        return item;
    }

    public ImageResource getImage() {
        return Utils.getImage(item.getImage());
    }

    public String getName() {
        return item.getName();
    }

    public DeviceType getType() {
        return item.getType();
    }

    public int getWigth() {
        return image.getWidth();
    }

    public int getHeight() {
        return image.getHeight();
    }

    public void setId(int id) {
        item.setId(id);
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
