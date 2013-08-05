package com.damintsev.client.uiframe;

import com.damintsev.client.Utils;
import com.damintsev.client.dao.Item;
import com.google.gwt.core.client.GWT;
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
public abstract class UIItem extends Label {

    private Item item;
    private Position position;

    public UIItem() {
        super();
        setText(getName());
        setHorizontalAlignment(ALIGN_CENTER);
        getElement().setInnerHTML(getElement().getInnerHTML() + "<br>");
        getElement().appendChild(new Image(Utils.getImage("hipath")).getElement());
//        Element div = DOM.createDiv();
//        div.setInnerText("div");
//        getElement().getParentNode().appendChild(div);
//        setResource(Utils.getImage("hipath"));

    }

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
