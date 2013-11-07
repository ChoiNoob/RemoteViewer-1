package com.damintsev.common.event;

/**
 * User: Damintsev Andrey
 * Date: 03.11.13
 * Time: 23:13
 */
public class ErrorEvent extends Event {

    private final static EventType EVENT_TYPE = EventType.CONNECTION_ERROR;

    @Override
    public EventType getType() {
        return EVENT_TYPE;
    }

    @Override
    public void onEvent() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
