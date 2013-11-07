package com.damintsev.common.utils;

import com.damintsev.common.event.ErrorEvent;
import com.damintsev.common.event.Event;
import com.damintsev.common.event.Events;
import com.sencha.gxt.widget.core.client.Window;

/**
 * User: Damintsev Andrey
 * Date: 22.10.13
 * Time: 22:59
 */
public class ConnectionError extends Window implements Events {

    public ConnectionError(Throwable throwable) {
        EVENT_HANDLER.fireEvent(new ErrorEvent());
        setPixelSize(250, 250);
    }
}
