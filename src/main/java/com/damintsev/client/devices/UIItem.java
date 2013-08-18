package com.damintsev.client.devices;

import com.damintsev.client.devices.enums.DeviceType;
import com.damintsev.client.devices.enums.Status;
import com.damintsev.client.uiframe.BusyChannelWindow;
import com.damintsev.utils.Position;
import com.damintsev.utils.Utils;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

/**
 * User: Damintsev Andrey
 * Date: 03.08.13
 * Time: 2:09
 */
public class UIItem extends Label {

    private Position position;
    private Image image;
    private Device data;
    private Label label;

    public UIItem(){

    }

    public UIItem(Device data) {
        super();
        this.data = data;
        init();
    }

    public UIItem(Device data, Position position) {
        super();
        this.data = data;
        this.position = position;
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
        super.addDoubleClickHandler(new DoubleClickHandler() {
            public void onDoubleClick(DoubleClickEvent event) {
                if(getDeviceType() == DeviceType.STATION) return;
                BusyChannelWindow panel = new BusyChannelWindow();
                panel.show(data);
            }
        });
        getElement().appendChild(image.getElement());

        label = new Label(getName());
        if(getName() != null || data instanceof Station)
            getElement().appendChild(label.getElement());
        label.setStyleName("tooltip");
    }

    public Position getCenterPosition() {
        int x = getAbsoluteLeft() + image.getWidth() / 2;
        int y = getAbsoluteTop() + image.getHeight() / 2;
        return new Position(x, y);
    }

    public Position getPosition() {
        if(position == null) savePosition();
        return position;
    }

//    public Item<T> getItem() {
//        item.setCoordX(getPosition().x);
//        item.setCoordY(getPosition().y);
//        return item;
//    }

    public ImageResource getImage() {
        return Utils.getImage(data.getImage());
    }

    public String getName() {
        return data.getName();
    }

    public DeviceType getDeviceType() {
        return data.getDeviceType();
    }

    public int getWidth() {
        return image.getWidth();
    }

    public int getHeight() {
        return image.getHeight();
    }

    public void setId(Long id) {
        data.setId(id);
    }
    
    public Long getId() {
        return data.getId();
    }

    public Station getStation() {
        return data.getStation();
    }

    public Status getStatus() {
        return data.getStatus();
    }

    public void setLabelColor() {
        label.getElement().getStyle().setBackgroundColor(data.getStatus().getColor());
    }

    public Device getData() {
        return data;
    }

    public void setData(Device data) {
        this.data = data;
    }
}
