package com.damintsev.common.event;

import com.damintsev.client.old.devices.Item;
import com.damintsev.common.uientity.TaskState;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author Damintsev Andrey
 *         28.01.14.
 */
public class AlarmEvent extends GwtEvent<AlarmEventHandler> {

    public static GwtEvent.Type<AlarmEventHandler> TYPE = new GwtEvent.Type<AlarmEventHandler>();

    private TaskState state;
    private Item item;

    public AlarmEvent(TaskState state, Item item) {
        this.state = state;
        this.item = item;
    }

    @Override
    public Type<AlarmEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AlarmEventHandler handler) {
       handler.onAlarmEdit(this);
    }

    public TaskState getState() {
        return state;
    }

    public Item getItem() {
        return item;
    }
}
