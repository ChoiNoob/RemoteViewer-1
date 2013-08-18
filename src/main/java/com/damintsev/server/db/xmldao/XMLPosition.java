package com.damintsev.server.db.xmldao;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by adamintsev
 * Date: 08.08.13 17:39
 */
public class XMLPosition {

    private Long id;

    private int positionX;

    private int positionY;

    public Long getId() {
        return id;
    }
    @XmlElement
    public void setId(Long id) {
        this.id = id;
    }

    public int getPositionX() {
        return positionX;
    }
    @XmlElement
    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }
    @XmlElement
    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }
}