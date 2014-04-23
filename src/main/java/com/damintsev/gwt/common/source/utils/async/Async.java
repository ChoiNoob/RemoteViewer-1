package com.damintsev.gwt.common.source.utils.async;

import com.google.gwt.core.client.RunAsyncCallback;

/**
 * User: Damintsev Andrey
 * Date: 22.10.13
 * Time: 22:32
 */
public class Async {

    public static void runAsync(final AsyncTask task) {
        com.google.gwt.core.client.GWT.runAsync(new RunAsyncCallback() {
            @Override
            public void onFailure(Throwable reason) {
                //Todo change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void onSuccess() {
                task.onSuccess();
            }
        });
    }
}
