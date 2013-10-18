package com.damintsev.client.old.devices;

import com.damintsev.common.pojo.Station;
import com.damintsev.common.pojo.TaskType;
import com.damintsev.common.visitor.CommonVisitor;
import com.damintsev.common.visitor.Visitor;
import com.damintsev.client.utils.Position;

import java.io.Serializable;

/**
 * User: Damintsev Andrey
 * Date: 05.08.13
 * Time: 22:33
 */
public abstract class Item implements Serializable, CommonVisitor {

    private Position position;

    public Position getPosition() {
        if(position == null) position = new Position(0,0); //если элемент создан с интерфейса
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setPosition(int x, int y) {
        this.position = new Position(x, y);
    }

    public abstract String getName();

    public abstract TaskType getType();

    public abstract Long getId();

    public abstract String getStringId();

    public abstract Station getStation();

    public abstract String getParentId();

    public abstract <T> T accept(Visitor<T> visitor);

    //    private int coordX;
//    private int coordY;
//    private Object data;
//
//    public Item() {
//
//    }

//    public Item(T data, Position pos) {
//        this.data = data;
//        coordX = pos.x;
//        coordY = pos.y;
//    }

//    public int getCoordX() {
//        return coordX;
//    }
//
//    public void setCoordX(int coordX) {
//        this.coordX = coordX;
//    }
//
//    public int getCoordY() {
//        return coordY;
//    }
//
//    public void setCoordY(int coordY) {
//        this.coordY = coordY;
//    }
//
//    public Object getData() {
//        return data;
//    }
//
//    public void setData(Object data) {
//        this.data = data;
//    }
//
//    public String getName() {
//        return "asdasd";//data.getName();
//    }
//
////    public DeviceType getType() {
////        return null;// data.getDeviceType();
////    }
//
//    public String getImage() {
//        return "cloud";//data.getImage();
//    }
//
//    public void setId(Long id) {
//        //data.setId(id);
//    }
//
//    public Long getId() {
//        return 5L;//data.getId();
//    }
}
