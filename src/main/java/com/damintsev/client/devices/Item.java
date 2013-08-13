package com.damintsev.client.devices;

import com.damintsev.client.devices.enums.DeviceType;
import com.damintsev.utils.Position;

import java.io.Serializable;

/**
 * User: Damintsev Andrey
 * Date: 05.08.13
 * Time: 22:33
 */
public class Item <T extends Device> implements Serializable {
   
    private int coordX;
    private int coordY;
    private T data;

    public Item() {

    }

    public Item(T data, Position pos) {
        this.data = data;
        coordX = pos.x;
        coordY = pos.y;
    }

    public int getCoordX() {
        return coordX;
    }

    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getName() {
        return data.getName();
    }

    public DeviceType getType() {
        return data.getDeviceType();
    }
    
    public String getImage() {
        return data.getImage();
    }

    public void setId(Long id) {
        data.setId(id);
    }

    public Long getId() {
        return data.getId();
    }
}
