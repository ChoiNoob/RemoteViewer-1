package com.damintsev.common.event;

import com.damintsev.common.utils.ConnectionError;

/**
 * User: Damintsev Andrey
 * Date: 03.11.13
 * Time: 23:13
 */
public abstract class Event {

    public abstract EventType getType();

    public abstract void onEvent();
}
