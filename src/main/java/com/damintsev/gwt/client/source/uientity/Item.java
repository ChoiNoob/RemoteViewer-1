package com.damintsev.gwt.client.source.uientity;

import com.damintsev.gwt.common.source.utils.Position;
import com.damintsev.gwt.client.source.visitor.CommonVisitor;
import com.damintsev.gwt.client.source.visitor.Visitor;
import com.google.gwt.user.client.rpc.IsSerializable;

import java.io.Serializable;

/**
 * User: Damintsev Andrey
 * Date: 05.08.13
 * Time: 22:33
 */
public abstract class Item implements IsSerializable, Serializable, CommonVisitor {

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

    public abstract Long getImageId();

}
