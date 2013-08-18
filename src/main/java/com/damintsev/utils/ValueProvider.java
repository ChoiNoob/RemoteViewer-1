package com.damintsev.utils;

/**
 * Created by adamintsev
 * Date: 14.08.13 15:22
 */
public abstract class ValueProvider<T, V> implements com.sencha.gxt.core.client.ValueProvider<T,V> {
    public abstract V getValue(T object);

    public void setValue(T object, V value) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getPath() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
