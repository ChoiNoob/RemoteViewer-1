package com.damintsev.utils;

import java.io.Serializable;

/**
 * Created by adamintsev
 * Date: 09.08.13 12:54
 */
public class Position {

    public Position(){

    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x;
    public int y;
}