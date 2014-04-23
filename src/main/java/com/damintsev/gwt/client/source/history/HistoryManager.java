package com.damintsev.gwt.client.source.history;

import com.damintsev.gwt.client.source.EventBus;
import com.damintsev.gwt.client.source.event.StartEditEvent;
import com.damintsev.gwt.client.source.event.StopEditEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;

/**
 * @author Damintsev Andrey
 *         22.04.2014.
 */
public class HistoryManager implements ValueChangeHandler<String> {

    public HistoryManager() {
        History.addValueChangeHandler(this);
        History.fireCurrentHistoryState();
    }

    @Override
    public void onValueChange(ValueChangeEvent<String> event) {
        String path = event.getValue();
        System.out.println("link=" + path);
        if (Link.LOGIN.toString().equals(path)) {
            //todo !!!!
        } else if (Link.EDIT.toString().equals(path)) {
            EventBus.get().fireEvent(new StartEditEvent());
        } else if (Link.MONITORING.toString().equals(path)) {
            EventBus.get().fireEvent(new StopEditEvent());
        } else {
            History.newItem("monitoring");
        }
    }
}