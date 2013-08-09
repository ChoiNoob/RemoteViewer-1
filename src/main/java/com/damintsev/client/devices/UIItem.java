package com.damintsev.client.devices;

import com.damintsev.client.devices.enums.DeviceType;
import com.damintsev.client.devices.enums.Status;
import com.damintsev.utils.Position;
import com.damintsev.utils.Utils;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.*;

/**
 * User: Damintsev Andrey
 * Date: 03.08.13
 * Time: 2:09
 */
public class UIItem<T extends Device> extends Label {

    private Item<T> item;
    private Position position;
    private Image image;

    public UIItem(Item<T> item) {
        super();
        this.item = item;
        position = new Position(item.getCoordX(), item.getCoordY());
        init();
    }

    public UIItem(T data) {
        super();
        this.item = new Item<T>(data);
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
        image = new Image(getImage());
        getElement().appendChild(image.getElement());
        Label label = new Label(getName());
        if(getName() != null)
            getElement().appendChild(label.getElement());
        label.setStyleName("tooltip");
    }

    public Position getCenterPosition() {
        int x = getAbsoluteLeft() + image.getWidth() / 2;
        int y = getAbsoluteTop() + image.getHeight() / 2;
        return new Position(x, y);
    }

    public Position getPosition() {
        return position;
    }

    public Item<T> getItem() {
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

    public int getWidth() {
        return image.getWidth();
    }

    public int getHeight() {
        return image.getHeight();
    }

    public void setId(int id) {
        item.setId(id);
    }
    
    public int getId() {
        return item.getId();
    }

    public Station getParentStation() {
        return item.getData().getParentStation();
    }

    public Status getStatus() {
        return item.getData().getStatus();
    }
}
