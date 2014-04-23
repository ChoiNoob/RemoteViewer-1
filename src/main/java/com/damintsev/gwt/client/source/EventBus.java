package com.damintsev.gwt.client.source;

import com.google.gwt.event.shared.HandlerManager;

/**
 * User: adamintsev
 * Date: 20.12.13
 * Bus to work with events
 */
public class EventBus {

    private static HandlerManager instance;

    public static HandlerManager get() {
        if (instance == null) instance = new HandlerManager(null);
        return instance;
    }
}
