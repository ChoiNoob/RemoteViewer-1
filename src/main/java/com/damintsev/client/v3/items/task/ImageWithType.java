package com.damintsev.client.v3.items.task;

import java.io.Serializable;

/**
 * User: Damintsev Andrey
 * Date: 13.10.13
 * Time: 12:35
 */
//todo удалить эту суку нафиг
public class ImageWithType implements Serializable {

    String type;
    String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
