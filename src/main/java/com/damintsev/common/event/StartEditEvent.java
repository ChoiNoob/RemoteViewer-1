package com.damintsev.common.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * User: adamintsev
 * Date: 20.12.13
 * //todo написать комментарии
 */
public class StartEditEvent extends GwtEvent<StartEditEventHandler> {

    public static Type<StartEditEventHandler> TYPE = new Type<StartEditEventHandler>();

    @Override
    public Type<StartEditEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(StartEditEventHandler handler) {
        handler.onStartEdit(this);
    }
}
