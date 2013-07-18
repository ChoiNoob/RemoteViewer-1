package com.damintsev.utils;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;

/**
* User: Damintsev Andrey
* Date: 18.07.13
* Time: 22:16
*/
public abstract class AValueProvider<T,V> implements ValueProvider<T,V> {
    public abstract V getValue(T object);

    public void setValue(T object, V value) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getPath() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
