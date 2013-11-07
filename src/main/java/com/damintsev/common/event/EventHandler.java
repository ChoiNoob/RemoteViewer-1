package com.damintsev.common.event;

import java.util.List;
import java.util.Map;

/**
 * User: Damintsev Andrey
 * Date: 03.11.13
 * Time: 23:28
 */
public class EventHandler {
    //todo поменять на тип
    Map<EventType, List<Event>> eventMap;


    public void fireEvent(Event event1) {

        List<Event> eventList = eventMap.get(event1.getType());
//        for(Event event)
    }
}
