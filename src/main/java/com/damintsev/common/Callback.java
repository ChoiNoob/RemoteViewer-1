package com.damintsev.common;

import com.damintsev.common.utils.Dialogs;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * User: Damintsev Andrey
 * Date: 21.10.13
 * Time: 21:04
 */
public abstract class Callback<T> implements AsyncCallback<T> {

    @Override
    public void onFailure(Throwable caught) {
//        Dialogs.alert();

    }

    @Override
    public void onSuccess(T result) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
