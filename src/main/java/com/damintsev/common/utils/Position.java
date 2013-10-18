package com.damintsev.common.utils;

import java.io.Serializable;

/**
 * Created by adamintsev
 * Date: 09.08.13 12:54
 */
public class Position implements Serializable{

    public Position(){

    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x;
    public int y;
}
