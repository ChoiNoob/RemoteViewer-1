package com.damintsev.client.devices;

import com.damintsev.client.devices.enums.DeviceType;
import com.damintsev.client.devices.enums.Status;
import com.damintsev.client.windows.BusyChannelWindow;
import com.damintsev.utils.Position;
import com.damintsev.utils.Utils;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

import java.io.Serializable;

/**
 * User: Damintsev Andrey
 * Date: 03.08.13
 * Time: 2:09
 */
public class UIItem extends Label implements Serializable {

    private Position position;
//    private Image image;
    private Device data;
//    private Label label;

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
        Image image = new Image(getImage());
        getElement().appendChild(image.getElement());

       Label label = new Label(getName());
        if(getName() != null || data instanceof Station)
            getElement().appendChild(label.getElement());
        label.setStyleName("tooltip");
        super.addDoubleClickHandler(new DoubleClickHandler() {
            public void onDoubleClick(DoubleClickEvent event) {
                if (getDeviceType() == DeviceType.STATION) return;
                BusyChannelWindow panel = new BusyChannelWindow();
                panel.show(UIItem.this);
            }
        });
    }

    public Position getCenterPosition() {
        int x = getAbsoluteLeft() + 100 / 2;
        int y = getAbsoluteTop() + 100 / 2;
        return new Position(x, y);
    }

    public Position getPosition() {
//        if(position == null) savePosition();
        return position;
    }
    
    public void setPosition(int x, int y) {
        this.position = new Position(x, y);
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
        return "lala";
//        return data.getName();
    }

    public DeviceType getDeviceType() {
        return DeviceType.ISDN;
    }

    public int getWidth() {
        return 50;//image.getWidth();
    }

    public int getHeight() {
        return 50;//image.getHeight();
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
//        label.getElement().getStyle().setBackgroundColor(data.getStatus().getColor());
    }

    public Device getData() {
        return data;
    }

    public void setData(Device data) {
        this.data = data;
    }

    public void redraw() {
        System.out.println("redraw");
//        label.getElement().setInnerText(getName());
////          label = new Label(getName());
//        getElement().removeChild(label.getElement());
//        label = new Label(getName());
//        getElement().appendChild(label.getElement());
    }
}
