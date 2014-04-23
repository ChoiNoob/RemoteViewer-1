package com.damintsev.gwt.client.source.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * User: adamintsev
 * Date: 20.12.13
 * //todo написать комментарии
 */
public class StopEditEvent extends GwtEvent<StopEditEventHandler> {

    public static Type<StopEditEventHandler> TYPE = new Type<StopEditEventHandler>();

    @Override
    public Type<StopEditEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(StopEditEventHandler handler) {
        handler.onStopEdit(this);
    }
}
