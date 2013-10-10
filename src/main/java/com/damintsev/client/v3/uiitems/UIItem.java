//package com.damintsev.client.v3.uiitems;
//
//import com.damintsev.utils.Position;
//import com.google.gwt.user.client.ui.IsWidget;
//import com.google.gwt.user.client.ui.Widget;
//
//import java.io.Serializable;
//
///**
// * User: Damintsev Andrey
// * Date: 09.10.13
// * Time: 22:30
// */
//public abstract class UIItem implements IsWidget, Serializable {
//
//    private Long id;
//    private int x;
//    private int y;
//
//    public abstract Widget asWidget();
//
//    /**
//     * Показывает от какого элемента к нему тянется
//     * @return
//     */
//    public abstract Long getSource();
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Position getPosition() {
//        return new Position(x, y);
//    }
//
//    public void setPosition(Position pos) {
//        this.x = pos.x;
//        this.y = pos.y;
//    }
//
//    public void setPosition(int x, int y) {
//        setPosition(new Position(x,y));
//    }
//
//}
