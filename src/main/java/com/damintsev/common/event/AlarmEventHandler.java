package com.damintsev.common.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * @author Damintsev Andrey
 *         28.01.14.
 */
public interface AlarmEventHandler extends EventHandler {

    void onAlarmEdit(AlarmEvent event);
}
